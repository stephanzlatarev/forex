package forex.account;

import forex.trace.Trace;

public class Quote {

  private static Trace trace = new Trace("quote");

  private long time = 0;
  private int buyPips = 0;
  private String buyQuote = null;
  private int sellPips = 0;
  private String sellQuote = null;

  private int highPips = 0;
  private int lowPips = 0;

  public Quote() {
  }

  public Quote(Quote quote) {
    this.time = quote.time;
    this.buyPips = quote.buyPips;
    this.buyQuote = quote.buyQuote;
    this.sellPips = quote.sellPips;
    this.sellQuote = quote.sellQuote;
    this.highPips = quote.highPips;
    this.lowPips = quote.lowPips;
  }

  public int getBuyPips() {
    return buyPips;
  }

  public String getBuyQuote() {
    return buyQuote;
  }

  public int getSellPips() {
    return sellPips;
  }

  public String getSellQuote() {
    return sellQuote;
  }

  public int getHighPips() {
    return highPips;
  }

  public int getLowPips() {
    return lowPips;
  }

  public long getTime() {
    return time;
  }

  public String toString() {
    return "quote: " + getBuyPips() + " - " + getSellPips();
  }

  public void setQuote(String buy, String sell) {
    if (!equals(buy, buyQuote) || !equals(sell, sellQuote)) {
      this.time = System.currentTimeMillis();
      this.buyQuote = buy;
      this.buyPips = pips(buy);
      this.sellQuote = sell;
      this.sellPips = pips(sell);

      History.getCandle(this.time).tick(this.buyPips);
      trace.trace(buy + " - " + sell);
    }
  }

  public void setQuote(long time, int buy, int sell) {
    this.time = time;
    this.buyPips = buy;
    this.sellPips = sell;
  }

  public void setQuote(long time, int buy, int sell, int high, int low) {
    this.time = time;
    this.buyPips = buy;
    this.sellPips = sell;
    this.highPips = high;
    this.lowPips = low;
  }

  private boolean equals(String a, String b) {
    if ((a == null) && (b == null)) {
      return true;
    } else if ((a == null) || (b == null)) {
      return false;
    } else {
      return a.equals(b);
    }
  }

  private int pips(String quote) {
    if (quote != null) {
      return (int) (Double.parseDouble(quote) * 100000);
    } else {
      return 0;
    }
  }

}
