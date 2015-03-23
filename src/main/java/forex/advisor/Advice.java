package forex.advisor;

import java.util.Properties;

public class Advice {

  public enum Action {
    Buy, // Open new long position
    Sell, // Open new short position
    Hold, // Hold the previously open position. Don't open a new one
    Close // Close the previously open position
  }

  public final static Advice BUY = new Advice(Action.Buy);
  public final static Advice SELL = new Advice(Action.Sell);
  public final static Advice HOLD = new Advice(Action.Hold, 0);
  public final static Advice CLOSE = new Advice(Action.Close, 0);

  private Action action;
  private int quantity;
  private Properties properties = new Properties();

  public Advice(Action action) {
    this(action, 10000);
  }

  public Advice(Action action, int quantity) {
    this.action = action;
    this.quantity = quantity;
  }

  public Action getAction() {
    return action;
  }

  public int getQuantity() {
    return quantity;
  }

  public Properties getProperties() {
    return properties;
  }

  public void setProperty(String key, String value) {
    properties.setProperty(key, value);
  }

  public String toString() {
    return action + " " + quantity;
  }

}
