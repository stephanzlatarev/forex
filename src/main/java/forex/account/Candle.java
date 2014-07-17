package forex.account;

public class Candle {

  private long time;
  private int open = 0;
  private int high = 0;
  private int low = 0;
  private int close = 0;

  public Candle(long time) {
    this.time = time;
  }

  public void tick(int price) {
    if (this.open == 0) {
      this.open = price;
    }

    if (this.high < price) {
      this.high = price;
    }

    if ((this.low == 0) || (this.low > price)) {
      this.low = price;
    }

    this.close = price;
  }

  public long getTime() {
    return time;
  }

  public int getOpen() {
    return open;
  }

  public int getHigh() {
    return high;
  }

  public int getLow() {
    return low;
  }

  public int getClose() {
    return close;
  }

}
