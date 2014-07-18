package forex.advisor;

public class TradeWindow {

  private int combo;
  private int startPips = 0;
  private long startTime;
  private long endTime;

  public TradeWindow(int combo, long startTime, long endTime) {
    this.combo = combo;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public boolean containsTime(long time) {
    return (time >= startTime) && (time <= endTime);
  }

  public boolean isInWatchPeriod(long time) {
    return (time >= startTime) && (time <= endTime);
  }

  public int getCombo() {
    return combo;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public int getStartPips() {
    return startPips;
  }

  public void setStartPips(int pips) {
    this.startPips = pips;
  }

  public String toString() {
    return startTime + "-" + endTime;
  }

}
