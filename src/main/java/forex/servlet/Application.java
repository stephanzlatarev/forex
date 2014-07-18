package forex.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import forex.account.Candle;
import forex.account.History;
import forex.advisor.MoonTrader;
import forex.advisor.TradeWindow;
import forex.trace.Trace;

@Controller
@EnableAutoConfiguration
public class Application {

  private static MoonTrader trader = new MoonTrader();
  private static LifeCycle life = new LifeCycle(trader);

  @RequestMapping(method=RequestMethod.GET, value="/", produces=MediaType.TEXT_PLAIN_VALUE)
  @ResponseBody
  String logs() {
    return Trace.logs();
  }

  @RequestMapping(method=RequestMethod.GET, value="/candles.json", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String window() {
    boolean addComma = false;
    StringBuilder out = new StringBuilder();

    out.append("[");

    for (Candle candle: History.getCandles()) {
      if (addComma) {
        out.append(",");
      }
      out.append("{");
  
      out.append("\"timestamp\":");
      out.append(String.valueOf(candle.getTime()));
      out.append(",");

      out.append("\"open\":");
      out.append(String.valueOf(candle.getOpen()));
      out.append(",");

      out.append("\"high\":");
      out.append(String.valueOf(candle.getHigh()));
      out.append(",");

      out.append("\"low\":");
      out.append(String.valueOf(candle.getLow()));
      out.append(",");

      out.append("\"close\":");
      out.append(String.valueOf(candle.getClose()));
  
      out.append("}");
      addComma = true;
    }

    out.append("]");

    return out.toString();
  }

  @RequestMapping(method=RequestMethod.GET, value="/advisor.json", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String advisor() {
    boolean addComma = false;
    StringBuilder out = new StringBuilder();

    out.append("[");

    for (TradeWindow window: trader.getTradeWindows()) {
      if (addComma) {
        out.append(",");
      }
      out.append("{");
  
      out.append("\"watchtime\":");
      out.append(String.valueOf(window.getStartTime()));
      out.append(",");

      out.append("\"opentime\":");
      out.append(String.valueOf(window.getStartTime()));
      out.append(",");

      out.append("\"closetime\":");
      out.append(String.valueOf(window.getEndTime()));
      out.append(",");

      out.append("\"watchpips\":");
      out.append(String.valueOf(110));
  
      out.append("}");
      addComma = true;
    }

    out.append("]");

    return out.toString();
  }

  public static void main(String[] args) {
    Trace.switchTrace("summary", true);
    Trace.switchTrace("memory", true);
    Trace.switchTrace("trade", true);
    Trace.switchTrace("connection", false);

    SpringApplication.run(Application.class, args);

    try {
      life.startClient();
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Ready for forex trading ...");
  }

}
