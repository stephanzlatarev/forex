package forex.service;

import java.io.IOException;

import forex.trader.TraderClient;

public class AccountTotalService {

  private TraderClient client;

  public AccountTotalService(TraderClient client) {
    this.client = client;
  }

  public double status() throws IOException {
    String data = client.getConnection().service(client, "dwr/call/plaincall/AccountService.getAccount.dwr",
        "c0-scriptName", "AccountService",
        "c0-methodName", "getAccount",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-param1", "boolean:false");

    int index = data.indexOf("s0.total=");
    if (index > 0) {
      String total = data.substring(index + 9, data.indexOf(";", index));

      return Double.parseDouble(total);
    }

    throw new IOException(data);
  }

}
