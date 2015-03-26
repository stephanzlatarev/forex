package forex.app;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import forex.service.HistoryService;
import forex.service.QuoteService;
import forex.trader.TraderClient;
import forex.wave.ComboService;

@Controller
@EnableAutoConfiguration
public class Application {

  private final static TimeZone GMT = TimeZone.getTimeZone("GMT");

  private TraderClient client = new TraderClient();
  private ComboService combos = new ComboService();
  private QuoteService quotes = new QuoteService().use(client);
  private HistoryService history = new HistoryService().use(client);

  public Application() {
    new Thread(new Broker(client)).start();
  }

  @RequestMapping(method=RequestMethod.GET, value="/history/", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String history() throws IOException {
    return new ObjectMapper().convertValue(history.getHistory(0, 100), JsonNode.class).toString();
  }

  @RequestMapping(method=RequestMethod.GET, value="/quote/", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String quote() throws IOException {
    return quotes.quote().toJson();
  }

  @RequestMapping(method=RequestMethod.GET, value="/combo/", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String combos() throws IOException {
    return new ObjectMapper().convertValue(combos.getWindows(), JsonNode.class).toString();
  }

  @RequestMapping(method=RequestMethod.GET, value="/chart/", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String chart() throws IOException {
    final int candleSize = 10;

    Calendar calendar = Calendar.getInstance(GMT);
    long time = calendar.getTimeInMillis();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 22);
    calendar.set(Calendar.MINUTE, 0);
    int candleCount = (int) ((time - calendar.getTimeInMillis()) / (1000L * 60 * candleSize));

    Object chart = quotes.chart(candleCount, candleSize);
    return new ObjectMapper().convertValue(chart, JsonNode.class).toString();
  }

  public static void main(String[] args) throws IOException {
    SpringApplication.run(Application.class, args);
  }

}
