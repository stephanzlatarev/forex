package forex.astronom;

import forex.astronom.util.LocationParser;

public class Place {

  public static Place UNKNOWN = new Place("unknown", "Earth", "???", "00E00" ,"00n00", "+0000");

  private String name;
  private String country;
  private String planet;
  private boolean north = true;
  private double latitude;
  private double longitude;
  private double zone;

  public Place(String name, String planet, String country, String longitude, String latitude, String zone) {
    this.name = name;
    this.planet = planet;
    this.country = country;

    boolean northSouth = true;
    double latitudeValue;
    double longitudeValue;
    double zoneValue;

    longitude = longitude.toUpperCase();
    latitude = latitude.toUpperCase();

    latitudeValue = LocationParser.parseLattitude(latitude);

    if (latitudeValue < 0) {
      latitudeValue = -latitudeValue;
      northSouth = false;
    }

    longitudeValue = LocationParser.parseLongitude(longitude);

    zoneValue = new Integer(zone.substring(1)).doubleValue() / 100;
    if (zone.charAt(0) == '-') {
      zoneValue *= -1;
    }

    this.longitude = longitudeValue;
    this.latitude = latitudeValue;
    this.zone = zoneValue;
    this.north = northSouth;
  }

  public boolean equals(Object obj) {
    if ((obj instanceof Place) && (getLongitude() == ((Place) obj).getLongitude()) && (getLatitude() == ((Place) obj).getLatitude())) {
      return true;
    }
    return false;
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }

  public String getPlanet() {
    return planet;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getZone() {
    return zone;
  }

  public boolean isNorth() {
    return north;
  }

  public boolean isSouth() {
    return !isNorth();
  }

  public String toString() {
    return getName() + ", " + getLongitude() + " " + getLatitude() + " " + (isNorth() ? "NORTH" : "SOUTH");
  }

}
