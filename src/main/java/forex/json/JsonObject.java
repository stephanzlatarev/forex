package forex.json;

public class JsonObject {

  private Object[] data;

  public JsonObject(Object... data) {
    this.data = data;
  }

  public String toString() {
    StringBuilder json = new StringBuilder();
    json.append("{");
    for (int i = 0; i < data.length; i += 2) {
      if (i > 0) {
        json.append(",");
      }

      json.append("\"");
      json.append(data[i]);
      json.append("\":");
      if ((data[i + 1] instanceof JsonObject) || (data[i + 1] instanceof JsonArray)) {
        json.append(data[i + 1].toString());
      } else {
        json.append("\"");
        json.append(data[i + 1]);
        json.append("\"");
      }
    }
    json.append("}");
    return json.toString();
  }

}
