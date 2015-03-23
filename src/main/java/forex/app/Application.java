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

import forex.service.QuoteService;
import forex.trader.TraderClient;

@Controller
@EnableAutoConfiguration
public class Application {

  private final static TimeZone GMT = TimeZone.getTimeZone("GMT");

  private TraderClient client = null;

  @RequestMapping(method=RequestMethod.GET, value="/quote/", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String quote() throws IOException {
    return new QuoteService(getClient()).quote().toJson();
  }

  @RequestMapping(method=RequestMethod.GET, value="/chart/", produces=MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  String chart() throws IOException {
    Calendar calendar = Calendar.getInstance(GMT);
    long time = calendar.getTimeInMillis();
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    calendar.set(Calendar.HOUR_OF_DAY, 22);
    calendar.set(Calendar.MINUTE, 0);
    time = (time - calendar.getTimeInMillis()) / (1000L * 60);

    Object chart = new QuoteService(getClient()).chart((int) time);
    return new ObjectMapper().convertValue(chart, JsonNode.class).toString();
  }

  private synchronized TraderClient getClient() throws IOException {
    if (client == null) {
      client = new TraderClient();
    }
    return client;
  }

  public static void main(String[] args) throws IOException {
    SpringApplication.run(Application.class, args);
  }

}
