package forex.astronom.planet;

import java.util.*;

import forex.astronom.Time;
import forex.astronom.util.Zodiac;

@SuppressWarnings("rawtypes")
public class PlanetSystem {

  protected Hashtable<String, Planet> planets = new Hashtable<String, Planet>();

  private Vector waiting = new Vector();

  public PlanetSystem() {
  }

  public Planet getPlanet(String name) {
    return (Planet) planets.get(name);
  }

  public Set<String> getPlanetNames() {
    return planets.keySet();
  }

  @SuppressWarnings("unchecked")
  public void calculate(Time event) {
    Enumeration objects = planets.elements();
    Planet planet;
    boolean goon = true;

    waiting.clear();

    while (objects.hasMoreElements()) {
      planet = (Planet) objects.nextElement();
      planet.markToBePositioned();
      waiting.add(planet);
    }

    while (goon) {
      goon = false;

      for (int i = 0; i < waiting.size(); i++) {
        planet = (Planet) waiting.elementAt(i);

        if (planet.position(event.getStandardYearTime())) {
          waiting.remove(planet);
          goon = true;
        }
      }
    }

    if (waiting.size() > 0) {
      System.err.println(" Unable to calculate position of planets. Circular dependencies.");
    }

    waiting.clear();
  }

  // get rid of this
  public void dumpZodiac(Time event) {
    Planet center = getPlanet(SolarSystem.EARTH);
    calculate(event);

    Enumeration objects = planets.elements();
    Planet planet;

    while (objects.hasMoreElements()) {
      planet = (Planet) objects.nextElement();
      System.out.println(" planet " + planet.getName() + " :  " + Zodiac.toString(planet.positionAround(center), "DD ZZZ MM SS") + " - " + planet.positionAround(center));
    }
  }

}