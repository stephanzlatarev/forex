package forex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import forex.parser.DwrParser;
import forex.trader.TraderClient;

public class QuoteService {

  private TraderClient client;

  public QuoteService(TraderClient client) {
    this.client = client;
  }

  public BuySellQuote quote() throws IOException {
    String data = client.getConnection().service(client, "dwr/call/plaincall/InstrumentsService.getMinMaxQuantities.dwr",
        "c0-scriptName", "InstrumentsService",
        "c0-methodName", "getMinMaxQuantities",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-e1", "string:EURUSD",
        "c0-param1", "Array:[reference:c0-e1]",
        "c0-param2", "string:Europe%2FSofia");

    List<Map<String, String>> entities = new DwrParser().parse(data, 1);

    return new BuySellQuote(entities.get(0));
  }

  public CandleChart chart(int candleCount, int candleSize) throws IOException {
    String data = client.getConnection().service(client, "dwr/call/plaincall/ChartService.getCandlesByCode.dwr",
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

    return new CandleChart(new DwrParser().parse(data, 2));
  }

}
