package forex.account;

import forex.trace.Trace;

public class Account {

  private Trace trace = new Trace("account");

  private String total = null;

  public String getTotal() {
    return total;
  }

  public void setTotal(String newTotal) {
    if (!newTotal.equals(total)) {
      total = newTotal;
  
      trace.trace(total);
    }
  }

  public String toString() {
    return "account total: " + getTotal();
  }

}
