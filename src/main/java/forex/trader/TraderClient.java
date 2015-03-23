package forex.trader;

import java.io.IOException;

public class TraderClient {

  private TraderConnection connection;
  private TraderSession session;

  public TraderClient() throws IOException {
    this(System.getProperty("forex.username", "trader.bg@mailimate.com"), System.getProperty("forex.password", "Abcd1234"));
  }

  public TraderClient(String username, String password) throws IOException {
    connection = new TraderConnection();
    session = new TraderSession(connection);
    session.login(username, password);
  }

  public TraderConnection getConnection() {
    return connection;
  }

  public TraderSession getSession() {
    return session;
  }

}
