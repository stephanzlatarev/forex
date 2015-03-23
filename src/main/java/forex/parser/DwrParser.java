package forex.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class DwrParser {

  public List<Map<String, String>> parse(String data, int from) {
    ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
    StringTokenizer tokens = new StringTokenizer(data, ";");

    int index = from;

    while (tokens.hasMoreTokens()) {
      String line = tokens.nextToken();

      if (line.startsWith("s" + (index + 1) + ".")) {
        populate(get(list, index - from + 1), line);
        index++;
      } else if (line.startsWith("s" + index + ".")) {
        populate(get(list, index - from), line);
      }
    }

    return list;
  }

  private Map<String, String> get(List<Map<String, String>> list, int index) {
    Map<String, String> entity = (index < list.size()) ? list.get(index) : null;

    if (entity == null) {
      entity = new HashMap<String, String>();
      list.add(entity);
    }

    return entity;
  }

  private void populate(Map<String, String> entity, String line) {
    StringTokenizer tokens = new StringTokenizer(line, "=");

    String key = tokens.nextToken();
    key = key.substring(key.indexOf('.') + 1);

    String value = tokens.nextToken();

    entity.put(key, value);
  }

}
