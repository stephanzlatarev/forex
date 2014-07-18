package forex.servlet;

import forex.advisor.Advisor;
import forex.trader.ClientListener;
import forex.trader.TraderClient;

public class LifeCycle implements ClientListener {

  private Advisor trader;
  private TraderClient client;

  public LifeCycle(Advisor trader) {
    this.trader = trader;
  }

  public synchronized void startClient() throws Exception {
    System.out.println(" trader client starting ...");

    String username = System.getProperty("forex.username", "trader.bg@mailimate.com");
    String password = System.getProperty("forex.password", "Abcd1234");

    client = new TraderClient(trader, this);
    client.connect(username, password);
    new Thread(client).start();

    System.out.println(" trader client started");
  }

  public synchronized void onDisconnect() {
    System.out.println(" trader client disconnected");
    try {
      client.close();

      System.out.println(" one minute cooldown ...");
      wait(60000);

      startClient();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
