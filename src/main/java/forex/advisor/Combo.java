package forex.advisor;

import forex.astronom.Time;
import forex.astronom.planet.MoonNode;
import forex.astronom.planet.Planet;
import forex.astronom.planet.SolarSystem;
import forex.astronom.util.Zodiac;

public class Combo {

  private final static SolarSystem SYSTEM = new SolarSystem();
  private final static Planet EARTH = SYSTEM.getPlanet(SolarSystem.EARTH);
  private final static Planet MOON = SYSTEM.getPlanet(SolarSystem.MOON);
  private final static Planet SUN = SYSTEM.getPlanet(SolarSystem.SUN);

  public static int calculate(long time) {
    return calculate(new Time(time));
  }

  public static int calculate(Time time) {
    return calculateZodiacSun(time);
  }

  private static int calculateZodiacSun(Time time) {
    SYSTEM.calculate(time);

    double moonSign = MOON.positionAround(EARTH);
    int comboSign = ((int) Zodiac.degree(moonSign) / 30) % 4 + 1;

    double moonSun = MOON.positionAround(EARTH) - SUN.positionAround(EARTH);
    int comboSun = ((int) Zodiac.degree(moonSun) / 30) % 4 + 1;

    return comboSign * 100 + comboSun;
  }

  private static int calculateZodiacNode(Time time) {
    SYSTEM.calculate(time);

    double moonSign = MOON.positionAround(EARTH);
    int comboSign = ((int) Zodiac.degree(moonSign) / 30) % 4 + 1;

    MoonNode node = new MoonNode();
    node.position(time.getStandardYearTime());
    double moonNode = node.getPosition(time);
    int comboNode = ((int) Zodiac.degree(moonNode) / 30) % 4 + 1;

    return comboSign * 100 + comboNode;
  }

  private static int calculateNodeSun(Time time) {
    SYSTEM.calculate(time);

    double moonSun = MOON.positionAround(EARTH) - SUN.positionAround(EARTH);
    int comboSun = ((int) Zodiac.degree(moonSun) / 30) % 4 + 1;

    MoonNode node = new MoonNode();
    node.position(time.getStandardYearTime());
    double moonNode = node.getPosition(time);
    int comboNode = ((int) Zodiac.degree(moonNode) / 30) % 4 + 1;

    return comboNode * 100 + comboSun;
  }

}
