package forex.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class CandleChart extends ArrayList<Candle> {

  public CandleChart(List<Map<String, String>> data) {
    for (Map<String, String> candle: data) {
      this.add(new Candle(candle));
    }
  }

}
