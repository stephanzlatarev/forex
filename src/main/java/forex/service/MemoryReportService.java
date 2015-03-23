package forex.service;

public class MemoryReportService {

  public static void memory() {
    int mb = 1024 * 1024;
    Runtime runtime = Runtime.getRuntime();

    long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / mb;
    long freeMemory = runtime.freeMemory() / mb;
    long totalMemory = runtime.totalMemory() / mb;
    long maxMemory = runtime.maxMemory() / mb;

    System.out.println("used: " + usedMemory + "MB; total: " + totalMemory + "MB; max: " + maxMemory + "MB; free: " + freeMemory + "MB");
  }

}
