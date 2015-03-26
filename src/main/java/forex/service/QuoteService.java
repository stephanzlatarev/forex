package forex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class QuoteService extends Service<QuoteService> {

  public BuySellQuote quote() throws IOException {
    List<Map<String, String>> entities = service("dwr/call/plaincall/InstrumentsService.getMinMaxQuantities.dwr",
        "c0-scriptName", "InstrumentsService",
        "c0-methodName", "getMinMaxQuantities",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-e1", "string:EURUSD",
        "c0-param1", "Array:[reference:c0-e1]",
        "c0-param2", "string:Europe%2FSofia");

    return new BuySellQuote(entities.get(1));
  }

  public CandleChart chart(int candleCount, int candleSize) throws IOException {
    List<Map<String, String>> entities = service("dwr/call/plaincall/ChartService.getCandlesByCode.dwr",
        "c0-scriptName", "ChartService",
        "c0-methodName", "getCandlesByCode",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-e2", "number:" + candleCount,
        "c0-e3", "string:EURUSD",
        "c0-e4", "number:" + (candleSize * 60000),
        "c0-e5", "number:0",
        "c0-e1", "Object_Object:{max:reference:c0-e2, code:reference:c0-e3, period:reference:c0-e4, timestamp:reference:c0-e5}",
        "c0-param1", "Array:[reference:c0-e1]",
        "c0-param2", "boolean:true");

    int skip = 0;
    for (Map<String, String> entry: entities) {
      if (entry.containsKey("timestamp")) {
        break;
      }
      skip++;
    }
    for (int i = 0; i < skip; i++) entities.remove(0);

    return new CandleChart(entities);
  }

}
