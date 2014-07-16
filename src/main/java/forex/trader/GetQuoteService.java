package forex.trader;

import com.fasterxml.jackson.databind.JsonNode;

import forex.json.JsonArray;
import forex.json.JsonObject;

public class GetQuoteService {

  private TraderClient client;

  public GetQuoteService(TraderClient client) {
    this.client = client;
  }

  public void subscribe() throws Exception {
    TraderConnection connection = client.getConnection();

    connection.poll("quotes/handshake",
        "version", "1.0",
        "minimumVersion", "0.9",
        "channel", "/meta/handshake",
        "supportedConnectionTypes", new JsonArray("long-polling", "callback-polling"),
        "advice", new JsonObject("timeout", "60000", "interval", "0"),
        "id", connection.getNextRequestId());

    connection.poll("quotes/",
        "channel", "/meta/subscribe",
        "subscription", "/EURUSD",
        "id", connection.getNextRequestId(),
        "clientId", connection.getClientId());
  }


  public void fetchQuote() throws Exception {
    TraderConnection connection = client.getConnection();

    JsonNode message = connection.poll("quotes/connect",
        "channel", "/meta/connect",
        "connectionType", "long-polling",
        "id", connection.getNextRequestId(),
        "clientId", connection.getClientId());

    if ("/EURUSD".equals(message.get(0).get("channel").asText())) {
      JsonNode data = message.get(0).get("data");

      client.getQuote().setQuote(data.get("buy").asText(), data.get("sell").asText());
    }
  }

}
