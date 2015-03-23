package forex.service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Candle extends HashMap<String, Object> {

  public Candle(Map<String, String> data) {
    this.put("timestamp", Long.parseLong(data.get("timestamp")));
    this.put("open", (int) (Double.parseDouble(data.get("open")) * 100000));
    this.put("high", (int) (Double.parseDouble(data.get("high")) * 100000));
    this.put("low", (int) (Double.parseDouble(data.get("low")) * 100000));
    this.put("close", (int) (Double.parseDouble(data.get("close")) * 100000));
  }

}
