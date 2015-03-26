package forex.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import forex.service.BuySellQuote;
import forex.service.QuoteService;
import forex.service.TradeService;
import forex.trader.TraderClient;

public class Broker implements Runnable {

  private QuoteService quotes;
  private TradeService trades;

  Broker(TraderClient client) {
    quotes = new QuoteService().use(client);
    trades = new TradeService().use(client);
  }

  @Override
  public synchronized void run() {
    final int quantity = 10000;

    boolean isBuy = true;
    List<String> positions = new ArrayList<String>();

    while (true) {
      try {
        BuySellQuote quote = quotes.quote();

        if (!positions.isEmpty()) {
          for (String position: positions) {
            trades.close(position);
          }
          positions.clear();
        } else if (isBuy) {
          List<String> ids = trades.open(quantity, quote.getSellQuote());
          if (!ids.isEmpty()) positions.addAll(ids);

          isBuy = false;
        } else {
          List<String> ids = trades.open(-quantity, quote.getBuyQuote());
          if (!ids.isEmpty()) positions.addAll(ids);

          isBuy = true;
        }

        wait(15 * 60 * 1000);
      } catch(IOException ioe) {
        System.err.println(ioe);
      } catch(InterruptedException ie) {
        return;
      }
    }
  }

}
