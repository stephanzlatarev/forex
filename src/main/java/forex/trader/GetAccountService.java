package forex.trader;

public class GetAccountService {

  private TraderClient client;

  public GetAccountService(TraderClient client) {
    this.client = client;
  }

  public void status() throws Exception {
    String data = client.getConnection().service(client, "dwr/call/plaincall/AccountService.getAccount.dwr",
        "c0-scriptName", "AccountService",
        "c0-methodName", "getAccount",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-param1", "boolean:false");

    if (data.indexOf("reconnect") > 0) {
      throw new IllegalStateException(data);
    } else {
      int index = data.indexOf("s0.total=");
      if (index > 0) {
        String total = data.substring(index + 9, data.indexOf(";", index));

        client.getAccount().setTotal(total);
      }
    }
  }

}
