package forex.trace;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
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
      logs.append(log + "\n");
//      System.out.println(log);
    }
  }

  public static void switchTrace(String module, boolean on) {
    switches.put(module, on);
  }

  public static String logs() {
    return logs.toString();
  }

  private static PrintStream ORIGINAL_OUT = System.out;
  private static PrintStream ORIGINAL_ERR = System.err;

  static {
    switchTrace("system.out", true);
    switchTrace("system.err", true);
    switchTrace("connection", true);
    switchTrace("session", true);

    System.setOut(new PrintStream(new OutputStream() {
      private Trace trace = new Trace("system.out");
      private StringBuilder line = new StringBuilder();
      public void write(int b) throws IOException {
        ORIGINAL_OUT.write(b);

        if (((char) b) == '\n') {
          trace.trace(line.toString());
          line = new StringBuilder();
        } else {
          line.append((char) b);
        }
      }
    }));
    System.setErr(new PrintStream(new OutputStream() {
      private Trace trace = new Trace("system.err");
      private StringBuilder line = new StringBuilder();
      public void write(int b) throws IOException {
        ORIGINAL_ERR.write(b);

        if (((char) b) == '\n') {
          trace.trace(line.toString());
          line = new StringBuilder();
        } else {
          line.append((char) b);
        }
      }
    }));
  }
}
