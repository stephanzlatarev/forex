package forex.service;

import java.util.Map;

public class BuySellQuote {

  private int buyPips = 0;
  private String buyQuote = null;
  private int sellPips = 0;
  private String sellQuote = null;

  public BuySellQuote(String buy, String sell) {
    this.buyQuote = buy;
    this.buyPips = pips(buy);
    this.sellQuote = sell;
    this.sellPips = pips(sell);
  }

  public BuySellQuote(Map<String, String> data) {
    this(data.get("buy"), data.get("sell"));
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

  public String toString() {
    return "[" + getBuyPips() + " - " + getSellPips() + "]";
  }

  public String toJson() {
    return "{\"buy\": " + getBuyPips() + ", \"sell\": " + getSellPips() + "}";
  }

  private int pips(String quote) {
    if (quote != null) {
      return (int) (Double.parseDouble(quote) * 100000);
    } else {
      return 0;
    }
  }

}
