package forex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TradeService extends Service<TradeService> {

  public List<String> open(int quantity, String price) throws IOException {
    List<Map<String, String>> response = service("dwr/call/plaincall/MarketOrderService.placeMarketOrder.dwr",
        "c0-scriptName", "MarketOrderService",
        "c0-methodName", "placeMarketOrder",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-param1", "string:EURUSD",
        "c0-param2", "number:" + price,
        "c0-param3", "number:" + quantity,
        "c0-param4", "null:null",
        "c0-param5", "null:null",
        "c0-param6", "string:NONE");

    List<String> ids = new ArrayList<String>();
    for (Map<String, String> entry: response) {
      if (entry.containsKey("positionId")) {
        ids.add(entry.get("positionId"));
      }
    }

    return ids;
  }

  public void close(String position) throws IOException {
    service("dwr/call/plaincall/MarketOrderService.closePosition.dwr",
        "c0-scriptName", "MarketOrderService",
        "c0-methodName", "closePosition",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-param1", "string:" + position,
        "c0-param2", "null:null");
  }

}
