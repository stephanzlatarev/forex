
package forex.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class Application {

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Forex trading ...";
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);

    System.out.println("Forex trading ...");
  }

}
