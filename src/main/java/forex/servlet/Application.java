package forex.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import forex.trace.Trace;

@Controller
@EnableAutoConfiguration
public class Application {

  private static LifeCycle life = new LifeCycle();

  @RequestMapping(method=RequestMethod.GET, value="/", produces=MediaType.TEXT_PLAIN_VALUE)
  @ResponseBody
  String logs() {
    return Trace.logs();
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
