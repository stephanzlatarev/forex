package forex.astronom.planet;

public class SolarSystem extends PlanetSystem {

  public static final String SUN = "Sun";
  public static final String MOON = "Moon";
  public static final String MERCURY = "Mercury";
  public static final String VENUS = "Venus";
  public static final String EARTH = "Earth";
  public static final String MARS = "Mars";
  public static final String JUPITER = "Jupiter";
  public static final String SATURN = "Saturn";
  public static final String URANUS = "Uranus";
  public static final String NEPTUNE = "Neptune";
  public static final String PLUTO = "Pluto";

  private Planet[] index = new Planet[11];

  public SolarSystem() {
    index(0, new Moon(this));
    index(1, new Sun(this));
    index(2, new Mercury(this));
    index(3, new Venus(this));
    index(4, new Mars(this));
    index(5, new Jupiter(this));
    index(6, new Saturn(this));
    index(7, new Uranus(this));
    index(8, new Neptune(this));
    index(9, new Pluto(this));
    index(10, new Earth(this));
  }

  private void index(int i, Planet planet) {
    planets.put(planet.getName(), planet);
    index[i] = planet;
    planet.index = i;
  }

  public Planet getPlanetByIndex(int i) {
    return index[i];
  }

}