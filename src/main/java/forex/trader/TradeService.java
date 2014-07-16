package forex.trader;

import forex.advisor.Advice;
import forex.trace.Trace;

public class TradeService {

  private Trace trace = new Trace("trade");

  private TraderClient client;
  private Advice position = null;

  public TradeService(TraderClient client) {
    this.client = client;
  }

  public void trade() throws Exception {
    Advice advice = client.getTrader().getAdvice(client.getQuote());

    switch (advice.getAction()) {
      case Buy: {
        closePreviousPostion();

        openNewPosition(advice.getQuantity(), client.getQuote().getBuyQuote());
        position = advice;
        return;
      }
      case Sell: {
        closePreviousPostion();

        openNewPosition(-advice.getQuantity(), client.getQuote().getSellQuote());
        position = advice;
        return;
      }
      case Hold: {
        return;
      }
      case Close: {
        closePreviousPostion();
        return;
      }
    }
  }

  private void closePreviousPostion() throws Exception {
    if (position != null) {
      switch (position.getAction()) {
        case Buy: {
          openNewPosition(-position.getQuantity(), client.getQuote().getSellQuote());
          position = null;
          return;
        }
        case Sell: {
          openNewPosition(position.getQuantity(), client.getQuote().getBuyQuote());
          position = null;
          return;
        }
        case Hold: {
          return;
        }
        case Close: {
          return;
        }
      }
    }
  }

  private void openNewPosition(int quantity, String price) throws Exception {
    trace.trace(quantity + " at " + price);

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
