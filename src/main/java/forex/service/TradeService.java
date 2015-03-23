package forex.service;

import java.io.IOException;

import forex.trader.TraderClient;

public class TradeService {

  private TraderClient client;

  public TradeService(TraderClient client) {
    this.client = client;
  }

  public void trade(int quantity, String price) throws IOException {
    client.getConnection().service(client, "dwr/call/plaincall/AccountService.trade.dwr",
        "c0-scriptName", "AccountService",
        "c0-methodName", "trade",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-e1", "string:EURUSD",
        "c0-e2", "string:EUR%2FUSD",
        "c0-param1", "Object_Object:{id:reference:c0-e1, code:reference:c0-e2, targetPrice:" + price + ", quantity:" + quantity + "}");
  }

}
