package forex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import forex.parser.DwrParser;
import forex.trader.TraderClient;

abstract class Service<T> {

  protected TraderClient client;

  @SuppressWarnings("unchecked")
  public T use(TraderClient client) {
    this.client = client;

    return (T) this;
  }

  protected final List<Map<String, String>> service(String endpoint, String... params) throws IOException {
    String response = client.getConnection().service(client, endpoint, params);

    if ((response == null) || response.contains("InvalidSessionException")) {
      client.connect();
      response = client.getConnection().service(client, endpoint, params);
    }
    
    List<Map<String, String>> list = new DwrParser().parse(response);
    return list;
  }

}
