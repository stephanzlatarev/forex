package forex.trader;

import forex.account.Account;
import forex.account.Quote;
import forex.advisor.Advisor;
import forex.trace.Trace;

public class TraderClient implements Runnable {

  private final static Trace trace = new Trace("summary");
  private final static Trace memory = new Trace("memory");

  private Account account = new Account();
  private Quote quote = new Quote();

  private Advisor trader;
  private ClientListener listener;

  private TraderConnection connection = new TraderConnection();
  private TraderSession session = new TraderSession(connection);

  private GetAccountService getAccountService = new GetAccountService(this);
  private GetQuoteService getQuoteService = new GetQuoteService(this);
  private TradeService tradeService = new TradeService(this);

  private boolean isClosed = false;

  public TraderClient(Advisor trader, ClientListener listener) {
    this.trader = trader;
    this.listener = listener;
  }

  public boolean isClosed() {
    return isClosed;
  }

  public Account getAccount() {
    return account;
  }

  public Quote getQuote() {
    return quote;
  }

  public Advisor getTrader() {
    return trader;
  }

  public TraderConnection getConnection() {
    return connection;
  }

  public TraderSession getSession() {
    return session;
  }

  public void close() {
    isClosed = true;
  }

  public void connect(String username, String password) throws Exception {
    trace.trace("login "+ username + ":" + password);
    session.login(username, password);
    getQuoteService.subscribe();
  }

  @Override
  public synchronized void run() {
    while (!isClosed()) {
      try {
        getAccountService.status();
        getQuoteService.fetchQuote();

        trace.trace(account + " " + quote);

        memory();

        tradeService.trade();
      } catch (Exception e) {
        e.printStackTrace();

        listener.onDisconnect();
        return;
      }
    }
  }

  public static void memory() {
    int mb = 1024 * 1024;
    Runtime runtime = Runtime.getRuntime();
    long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / mb;
    long freeMemory = runtime.freeMemory() / mb;
    long totalMemory = runtime.totalMemory() / mb;
    long maxMemory = runtime.maxMemory() / mb;

    memory.trace("used: " + usedMemory + "MB; total: " + totalMemory + "MB; max: " + maxMemory + "MB; free: " + freeMemory + "MB");
  }
}
