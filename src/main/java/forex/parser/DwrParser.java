package forex.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DwrParser {

  public List<Map<String, String>> parse(String data) {
    ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
    StringTokenizer tokens = new StringTokenizer(data, ";");

    while (tokens.hasMoreTokens()) {
      String line = tokens.nextToken();
      int dot = line.indexOf('.');

      if (line.startsWith("s") && (dot > 0)) {
        int index = Integer.parseInt(line.substring(1, dot));
        populate(get(list, index), line.substring(dot + 1));
      }
    }

    return list;
  }

  private Map<String, String> get(List<Map<String, String>> list, int index) {
    while (index >= list.size()) {
      list.add(new HashMap<String, String>());
    }

    return list.get(index);
  }

  private void populate(Map<String, String> entity, String line) {
    StringTokenizer tokens = new StringTokenizer(line, "=");

    String key = tokens.nextToken();
    String value = tokens.nextToken();

    if (value.startsWith("\"") && value.endsWith("\"")) {
      value = value.substring(1, value.length() - 1);
    }

    entity.put(key, value);
  }

}
