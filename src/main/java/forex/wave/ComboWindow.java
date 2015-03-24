package forex.wave;

public class ComboWindow {

  private int combo;
  private long startTime;
  private long endTime;

  public ComboWindow(int combo, long startTime, long endTime) {
    this.combo = combo;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  public boolean containsTime(long time) {
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

  public String toString() {
    return startTime + "-" + endTime;
  }

}