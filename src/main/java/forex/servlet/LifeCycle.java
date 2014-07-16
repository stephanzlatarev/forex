package forex.servlet;

import forex.advisor.Advisor;
import forex.advisor.MoonTrader;
import forex.trader.ClientListener;
import forex.trader.TraderClient;

public class LifeCycle implements ClientListener {

  private Advisor trader = new MoonTrader();
  private TraderClient client;

  public synchronized void startClient() throws Exception {
    System.out.println(" trader client starting ...");

    client = new TraderClient(trader, this);
    client.connect(System.getProperty("forex.username"), System.getProperty("forex.password"));
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
