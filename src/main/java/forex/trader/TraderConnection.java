package forex.trader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ProxySelector;
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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import forex.parser.JsonArray;
import forex.parser.JsonObject;

public class TraderConnection {

  private Set<String> cookies = new HashSet<String>();

  private String url;
  private HttpClient http;

  private String clientId = null;
  private int requestId = 0;
  private int serviceBatchId = 1;

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

  private synchronized HttpResponse request(HttpUriRequest request) throws IOException {
    return http.execute(request);
  }

  public String getForPage(String endpoint, Map<String, String> headers) throws IOException {
    HttpGet get = new HttpGet(url + endpoint);

    if (headers != null) {
      for (Map.Entry<String, String> header: headers.entrySet()) {
        get.setHeader(header.getKey(), header.getValue());
      }
    }
    handleRequestCookies(get);

    HttpResponse response = request(get);
    handleResponseCookies(response);

    StringBuilder text = new StringBuilder();
    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
    String line = "";
    while ((line = reader.readLine()) != null) {
      text.append(line);
    }
    reader.close();

    return text.toString();
  }

  @SuppressWarnings("deprecation")
  public void post(String endpoint, String data, Map<String, String> headers) throws IOException {
    HttpResponse response = postForResponse(endpoint, data, headers);
    response.getEntity().consumeContent();
  }

  public JsonNode poll(String endpoint, Object... data) throws IOException {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Connection", "keep-alive");
    headers.put("Content-Type", "application/json; charset=UTF-8");

    return postForJson(endpoint, new JsonArray(new JsonObject(data)).toString(), headers);
  }

  public JsonNode postForJson(String endpoint, String data, Map<String, String> headers) throws IOException {
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

    return message;
  }

  private HttpResponse postForResponse(String endpoint, String data, Map<String, String> headers) throws IOException {
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

    return response;
  }

  public String service(TraderClient client, String endpoint, String... params) throws IOException {
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
    String message = read(response.getEntity().getContent());

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

  private JsonNode toJson(InputStream stream) throws IOException {
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

  private String read(InputStream stream) throws IOException {
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
      X509HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
      SSLContext sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
      TrustManager trustManager = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] chain, String authType) {}
        public void checkServerTrusted(X509Certificate[] chain, String authType) {}
        public X509Certificate[] getAcceptedIssuers() { return null; }
      };
  
      sslContext.init(null, new TrustManager[] { trustManager }, null);
  
      SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
      SystemDefaultRoutePlanner routePlanner = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
      CloseableHttpClient httpclient = HttpClients.custom()
          .setSSLSocketFactory(connectionSocketFactory)
          .setRoutePlanner(routePlanner)
          .build();
      return httpclient;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
