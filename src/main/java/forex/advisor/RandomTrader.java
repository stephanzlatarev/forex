package forex.advisor;

import forex.account.Quote;

public class RandomTrader implements Advisor {

  private int position = 0;

  public Advice getAdvice(Quote quote) {
    int pips = quote.getBuyPips();

    if (Math.abs(pips - position) > 10) {
      if (position == 0) {
        position = pips;
        return Advice.BUY;
      } else {
        position = 0;
        return Advice.CLOSE;
      }
    }

    return Advice.HOLD;
  }

}
