package forex.trader;

import java.io.IOException;

public class TraderClient {

  private String username;
  private String password;

  private TraderConnection connection = null;
  private TraderSession session = null;

  public TraderClient() {
    this(System.getProperty("forex.username", "trader.bg@mailimate.com"), System.getProperty("forex.password", "Abcd1234"));
  }

  public TraderClient(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public TraderConnection getConnection() throws IOException {
    if (connection == null) connect();

    return connection;
  }

  public TraderSession getSession() throws IOException {
    if (connection == null) connect();

    return session;
  }

  public void connect() throws IOException {
    connection = new TraderConnection();
    session = new TraderSession(connection);
    session.login(username, password);
  }

}
