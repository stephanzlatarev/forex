package forex.json;

public class JsonArray {

  private Object[] data;

  public JsonArray(Object... data) {
    this.data = data;
  }

  public String toString() {
    StringBuilder json = new StringBuilder();
    json.append("[");
    for (int i = 0; i < data.length; i++) {
      if (i > 0) {
        json.append(",");
      }

      if ((data[i] instanceof JsonObject) || (data[i] instanceof JsonArray)) {
        json.append(data[i].toString());
      } else {
        json.append("\"");
        json.append(data[i]);
        json.append("\"");
      }
    }
    json.append("]");
    return json.toString();
  }

}
