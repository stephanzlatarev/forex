package forex.account;

import java.util.ArrayList;
import java.util.List;

public class History {

  private static ArrayList<Candle> candles = new ArrayList<Candle>();

  public static Candle getCandle(long time) {
    Candle candle = new Candle(time);
    candles.add(candle);
    return candle;
  }

  public static List<Candle> getCandles() {
    return candles;
  }

}
