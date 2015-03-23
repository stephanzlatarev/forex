package forex.trader;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;

public class TraderSession {

  private TraderConnection connection;
  private String random;
  private String ticket;
  private String sessionId;

  public TraderSession(TraderConnection connection) {
    this.connection = connection;
  }

  public void login(String username, String password) throws IOException {
    connection.setUrl("https://www.trader.bg/");

    this.random = getForRandom();
    this.ticket = postForTicket(username, password);

    connection.setUrl("https://demo.trader.bg/");

    postForLogin();

    sessionId = getForSession();
  }

  public String getSessionId() {
    return sessionId;
  }

  private String getForSession() throws IOException {
    connection.getForPage("", null);

    for (String cookie: connection.getCookies()) {
      if (cookie.startsWith("JSESSIONID")) {
        int index = cookie.indexOf("=");
        return cookie.substring(index + 1);
      }
    }
    return null;
  }

  private String getForRandom() throws IOException {
    final String PREFIX_RANDOM = "<input type=\"hidden\" class=\"form-control\" name=\"rand\" value=\"";

    String page = connection.getForPage("bg/%D0%92%D1%85%D0%BE%D0%B4/", null);

    int index = page.indexOf(PREFIX_RANDOM);
    if (index > 0) {
      index += PREFIX_RANDOM.length();
      return page.substring(index, page.indexOf("\"", index));
    } else {
      throw new IOException("No random token!");
    }
  }

  private String postForTicket(String username, String password) throws IOException {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("X-Requested-With", "XMLHttpRequest");
    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    String data = "accountType=demo&rand=" + random + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + password + "&rememberMe=1";

    JsonNode json = connection.postForJson("bg/accounts/loginCustomer/", data, headers);

    if ("error".equals(json.get("status").asText())) {
      throw new IOException(json.get("data").get(0).get("message").asText());
    }

    return json.get("data").get(0).get("info").get("params").get("ticket").asText();
  }

  private void postForLogin() throws IOException {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    String data = "accountId=453796&engineId=0&ticket=" + ticket + "&rand=" + random;

    connection.post("login", data, headers);
  }

}
