package forex.trader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import forex.json.JsonArray;
import forex.json.JsonObject;
import forex.trace.Trace;

@SuppressWarnings("deprecation")
public class TraderConnection {

  private static Trace trace = new Trace("connection");

  private Set<String> cookies = new HashSet<String>();

  private String url;
  private HttpClient http;

  private String clientId = null;
  private int requestId = 0;

  public TraderConnection() {
    this.http = getNewHttpClient();
  }

  void setUrl(String url) {
    this.url = url;
  }

  public String getClientId() {
    return clientId;
  }

  public int getNextRequestId() {
    return ++requestId;
  }

  public Set<String> getCookies() {
    return cookies;
  }

  private synchronized HttpResponse request(HttpUriRequest request) throws Exception {
    return http.execute(request);
  }

  public String getForPage(String endpoint, Map<String, String> headers) throws Exception {
    trace.trace(">> GET " + url + endpoint);
    HttpGet get = new HttpGet(url + endpoint);

    if (headers != null) {
      for (Map.Entry<String, String> header: headers.entrySet()) {
        get.setHeader(header.getKey(), header.getValue());
      }
    }
    handleRequestCookies(get);

    HttpResponse response = request(get);
    handleResponseCookies(response);

    trace.trace("<< " + response);

    StringBuilder text = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    String line = "";
    while ((line = reader.readLine()) != null) {
      text.append(line);
    }
    reader.close();

    return text.toString();
  }

  public void post(String endpoint, String data, Map<String, String> headers) throws Exception {
    HttpResponse response = postForResponse(endpoint, data, headers);
    response.getEntity().consumeContent();
  }

  public JsonNode poll(String endpoint, Object... data) throws Exception {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Connection", "keep-alive");
    headers.put("Content-Type", "application/json; charset=UTF-8");

    return postForJson(endpoint, new JsonArray(new JsonObject(data)).toString(), headers);
  }

  public JsonNode postForJson(String endpoint, String data, Map<String, String> headers) throws Exception {
    HttpResponse response = postForResponse(endpoint, data, headers);
    JsonNode message = toJson(response.getEntity().getContent());

    if ((message != null) && (message.get(0) != null)) {
      JsonNode isSuccessful = message.get(0).get("successful");
      if ((isSuccessful != null) && !isSuccessful.asBoolean()) {
        throw new IllegalStateException(message.toString());
      }

      if (clientId == null) {
        String newClientId = message.get(0).get("clientId").asText();
        if (newClientId != null) {
          clientId = newClientId;
        }
      }
    }

    trace.trace("<< " + message);
    return message;
  }

  private HttpResponse postForResponse(String endpoint, String data, Map<String, String> headers) throws Exception {
    trace.trace(">> POST " + url + endpoint + " " + data);
    HttpPost post = new HttpPost(url + endpoint);

    if (headers != null) {
      for (Map.Entry<String, String> header: headers.entrySet()) {
        post.setHeader(header.getKey(), header.getValue());
      }
    }
    handleRequestCookies(post);
    post.setEntity(new StringEntity(data));

    HttpResponse response = request(post);
    handleResponseCookies(response);

    trace.trace("<< " + response);
    return response;
  }

  private int serviceBatchId = 1;
  public String service(TraderClient client, String endpoint, String... params) throws Exception {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Connection", "keep-alive");
    headers.put("Content-Type", "application/json; charset=UTF-8");

    StringBuilder data = new StringBuilder();
    data.append("callCount=1\r\n");
    data.append("httpSessionId=" + client.getSession().getSessionId() + "\r\n");
    data.append("scriptSessionId=43DE9CEDF4FED6E5C911875150FF0690805\r\n");
    for (int i = 0; i < params.length; i += 2) {
      data.append(params[i]);
      data.append("=");
      data.append(params[i + 1]);
      data.append("\r\n");
    }
    data.append("batchId=" + (serviceBatchId++) + "\r\n");

    HttpResponse response = postForResponse(endpoint, data.toString(), headers);
    String message = toString(response.getEntity().getContent());

    trace.trace("<< " + message);
    return message;
  }

  private void handleRequestCookies(HttpRequestBase request) {
    String cookieValue = "";
    for (String cookie: cookies) {
      cookieValue += cookie + "; ";
    }
    request.setHeader("Cookie", cookieValue);
  }

  private void handleResponseCookies(HttpResponse response) {
    for (Header header: response.getAllHeaders()) {
      if ("Set-Cookie".equalsIgnoreCase(header.getName())) {
        cookies.add(header.getValue().substring(0, header.getValue().indexOf(";")));
      }
    }
  }

  private JsonNode toJson(InputStream stream) throws Exception {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String line = reader.readLine();

    try {
      if (line != null) {
        return new ObjectMapper().readTree(line);
      } else {
        return null;
      }
    } finally {
      reader.close();
    }
  }

  public String toString(InputStream stream) throws Exception {
    StringBuilder text = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
    String line = "";
    while ((line = reader.readLine()) != null) {
      text.append(line);
    }
    reader.close();

    return text.toString();
  }

  private HttpClient getNewHttpClient() {
    try {
      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      trustStore.load(null, null);

      SSLSocketFactory sf = new NaiveSSLSocketFactory(trustStore);
      sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

      HttpParams params = new BasicHttpParams();

      HttpConnectionParams.setConnectionTimeout(params, 60000);
      HttpConnectionParams.setSoTimeout(params, 60000);

      HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

      SchemeRegistry registry = new SchemeRegistry();
      registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      registry.register(new Scheme("https", sf, 443));

      ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

      return new DefaultHttpClient(ccm, params);
    } catch (Exception e) {
      return new DefaultHttpClient();
    }
  }

  static {
    try {
      InetAddress.getByName("www.trader.bg");
    } catch (UnknownHostException e) {
      System.err.println(e.toString());
    }
  }

  class NaiveSSLSocketFactory extends SSLSocketFactory {
    SSLContext sslContext = SSLContext.getInstance("TLS");

    public NaiveSSLSocketFactory(KeyStore truststore) throws Exception {
      super(truststore);

      TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
          return null;
        }
      };

      sslContext.init(null, new TrustManager[] { tm }, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
      return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }

    @Override
    public Socket createSocket() throws IOException {
      return sslContext.getSocketFactory().createSocket();
    }
  }

}
