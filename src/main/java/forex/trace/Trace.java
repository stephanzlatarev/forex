package forex.trace;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Trace {

  private static HashMap<String, Boolean> switches = new HashMap<String, Boolean>();
  private static StringBuilder logs = new StringBuilder();
  private static SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy HH:mm");

  private String module;

  public Trace(String module) {
    this.module = module;
  }

  public synchronized void trace(String message) {
    if (switches.containsKey(module) && switches.get(module)) {
      String log = date.format(new Date()) + " [" + module + "] " + message;
      logs.append(log + "\r\n");
      System.out.println(log);
    }
  }

  public static void switchTrace(String module, boolean on) {
    switches.put(module, on);
  }

  public static String logs() {
    return logs.toString();
  }

}
