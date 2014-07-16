package forex.astronom.util;

public class LocationParser {

  public static double parseLattitude(String latitude) {
    latitude = latitude.toUpperCase();

    int direction = 1;
    int index = latitude.indexOf('N');

    if (index < 0) {
      index = latitude.indexOf('S');
      direction = -1;
    }

    return direction * (new Integer(latitude.substring(0, index)).doubleValue() + (new Integer(latitude.substring(index + 1)).doubleValue() / 60));
  }

  public static double parseLongitude(String longitude) {
    longitude = longitude.toUpperCase();

    int direction = 1;
    int index = longitude.indexOf('W');

    if (index < 0) {
      index = longitude.indexOf('E');
      direction = -1;
    }

    return direction * (new Integer(longitude.substring(0, index)).doubleValue() + (new Integer(longitude.substring(index + 1)).doubleValue() / 60));
  }

  public static double parseTimeZone(String timezone) {
  	return Double.parseDouble(timezone) / 100;
  }

}
