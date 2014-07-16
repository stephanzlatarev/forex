package forex.advisor;

public class Advice {

  public enum Action {
    Buy, // Open new long position
    Sell, // Open new short position
    Hold, // Hold the previously open position. Don't open a new one
    Close // Close the previously open position
  }

  public final static Advice BUY = new Advice(Action.Buy, 10000);
  public final static Advice SELL = new Advice(Action.Sell, 10000);
  public final static Advice HOLD = new Advice(Action.Hold, 0);
  public final static Advice CLOSE = new Advice(Action.Close, 0);

  private Action action;
  private int quantity;

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

  public String toString() {
    return action + " " + quantity;
  }

}
