package forex.account;

import forex.trace.Trace;

public class Quote {

  private static Trace trace = new Trace("quote");

  private long time = 0;
  private int buyPips = 0;
  private String buyQuote = null;
  private int sellPips = 0;
  private String sellQuote = null;

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

      trace.trace(buy + " - " + sell);
    }
  }

  public void setQuote(long time, int buy, int sell) {
    this.time = time;
    this.buyPips = buy;
    this.sellPips = sell;
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
