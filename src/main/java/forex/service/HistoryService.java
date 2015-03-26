package forex.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HistoryService extends Service<HistoryService> {

  public List<Map<String, String>> getHistory(int pageOffset, int pageSize) throws IOException {
    List<Map<String, String>> list = service("dwr/call/plaincall/ReportsService.getOrder.dwr",
        "c0-scriptName", "ReportsService",
        "c0-methodName", "getOrder",
        "c0-id", "0",
        "c0-param0", "boolean:false",
        "c0-param1", "string:01.01.2015",
        "c0-param2", "string:01.01.2100",
        "c0-param3", "string:Europe%2FSofia",
        "c0-param4", "string:all",
        "c0-param5", "number:" + pageOffset,
        "c0-param6", "number:" + pageSize,
        "c0-param7", "string:modified",
        "c0-param8", "number:-1");
    list.remove(0);
    return list;
  }

}
