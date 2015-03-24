package forex.wave;

import java.util.ArrayList;
import java.util.List;

import forex.astronom.Time;
import forex.astronom.planet.Planet;
import forex.astronom.planet.SolarSystem;
import forex.astronom.util.Zodiac;

public class ComboService {

  private final SolarSystem SYSTEM = new SolarSystem();
  private final Planet EARTH = SYSTEM.getPlanet(SolarSystem.EARTH);
  private final Planet MOON = SYSTEM.getPlanet(SolarSystem.MOON);
  private final Planet SUN = SYSTEM.getPlanet(SolarSystem.SUN);

  private long weekStart = 0;
  private long weekEnd = 0;
  private List<ComboWindow> windows = new ArrayList<ComboWindow>();

  public List<ComboWindow> getWindows() {
    findTradeWindows(System.currentTimeMillis());

    return windows;
  }

  private synchronized void findTradeWindows(long weekTime) {
    if ((weekStart <= weekTime) && (weekTime <= weekEnd)) {
      return;
    } else {
      windows.clear();
    }

    Time time = new Time(weekTime);
    time.set(Time.DAY_OF_WEEK, Time.SUNDAY);
    time.set(Time.HOUR_OF_DAY, 22);
    time.set(Time.MINUTE, 0);
    time.set(Time.SECOND, 0);
    time.set(Time.MILLISECOND, 0);

    weekStart = time.getTimeInMillis();
    weekEnd = weekStart + 1000L * 60 * 60 * 24 * 5;

    int windowCombo = calculateCombo(time);
    long windowOpenTime = weekStart;

    while (time.getTimeInMillis() < weekEnd) {
      time.add(Time.MINUTE, 1);

      int newCombo = calculateCombo(time);
      if (newCombo != windowCombo) {
        windows.add(new ComboWindow(windowCombo, windowOpenTime, time.getTimeInMillis()));

        windowOpenTime = time.getTimeInMillis();
        windowCombo = newCombo;
      }
    }

    windows.add(new ComboWindow(windowCombo, windowOpenTime, weekEnd));

    // expand week time bound to include weekends so that trade window in not recalculated for weekends
    weekStart -= 1000L * 60 * 60 * 22; // Sunday
    weekStart -= 1000L * 60 * 60 * 24; // Saturday
    weekStart -= 1000L * 60 * 60 * 2;  // Friday
    weekEnd += 1000L * 60 * 60 * 2;    // Friday
    weekEnd += 1000L * 60 * 60 * 24;   // Saturday
    weekEnd += 1000L * 60 * 60 * 22;   // Saturday
  }

  private int calculateCombo(Time time) {
    SYSTEM.calculate(time);

    double moonSign = MOON.positionAround(EARTH);
    int comboSign = ((int) Zodiac.degree(moonSign) / 30) % 4 + 1;

    double moonSun = MOON.positionAround(EARTH) - SUN.positionAround(EARTH);
    int comboSun = ((int) Zodiac.degree(moonSun) / 30) % 4 + 1;

    return comboSign * 100 + comboSun;
  }

}
