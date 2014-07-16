package forex.trader;

import java.net.URLEncoder;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;

import forex.trace.Trace;

public class TraderSession {

  private final static Trace trace = new Trace("session");

  private TraderConnection connection;
  private String random;
  private String ticket;
  private String sessionId;

  public TraderSession(TraderConnection connection) {
    this.connection = connection;
  }

  public void login(String username, String password) throws Exception {
    connection.setUrl("https://www.trader.bg/");

    this.random = getForRandom(); trace.trace("random: " + random);
    this.ticket = postForTicket(username, password); trace.trace("ticket: " + ticket);

    connection.setUrl("https://demo.trader.bg/");

    postForLogin();

    sessionId = getForSession(); trace.trace("session: " + sessionId);

    trace.trace("authentication complete");
  }

  public String getSessionId() {
    return sessionId;
  }

  private String getForSession() throws Exception {
    connection.getForPage("", null);

    for (String cookie: connection.getCookies()) {
      if (cookie.startsWith("JSESSIONID")) {
        int index = cookie.indexOf("=");
        return cookie.substring(index + 1);
      }
    }
    return null;
  }

  private String getForRandom() throws Exception {
    String page = connection.getForPage("bg/%D0%92%D1%85%D0%BE%D0%B4/", null);

    int index = page.indexOf("<input type=\"hidden\" name=\"rand\"");
    if (index > 0) {
      index += 40; // skipping to opening quotes
      return page.substring(index, page.indexOf("\"", index));
    } else {
      throw new IllegalStateException("No random token!");
    }
  }

  private String postForTicket(String username, String password) throws Exception {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("X-Requested-With", "XMLHttpRequest");
    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    String data = "accountType=demo&rand=" + random + "&username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + password + "&rememberMe=1";

    JsonNode json = connection.postForJson("bg/accounts/loginCustomer/", data, headers);

    return json.get("data").get(0).get("info").get("params").get("ticket").asText();
  }

  private void postForLogin() throws Exception {
    HashMap<String, String> headers = new HashMap<String, String>();
    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

    String data = "accountId=453796&engineId=0&ticket=" + ticket + "&rand=" + random;

    connection.post("login", data, headers);
  }

}
