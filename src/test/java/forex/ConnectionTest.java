package forex;

import org.junit.Test;

import forex.account.Quote;
import forex.advisor.Advice;
import forex.advisor.Advisor;
import forex.trader.ClientListener;
import forex.trader.TraderClient;

public class ConnectionTest {

  @Test public void connect() throws Exception {
    System.out.println(" trader client starting ...");

    String username = System.getProperty("forex.username", "trader.bg@mailimate.com");
    String password = System.getProperty("forex.password", "Abcd1234");

    Advisor advisor = new Advisor() {
      public Advice getAdvice(Quote quote) {
        System.out.println("quote: " + quote);
        return Advice.HOLD;
      }
    };

    ClientListener listener = new ClientListener() {
      public void onDisconnect() {
      }
    };

    TraderClient client = new TraderClient(advisor, listener);
    client.connect(username, password);
    client.cycle();

    System.out.println(" account: " + client.getAccount());
    System.out.println(" quote: " + client.getQuote());
  }

}
