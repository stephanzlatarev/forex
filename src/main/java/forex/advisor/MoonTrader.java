package forex.advisor;

import java.util.ArrayList;
import java.util.List;

import forex.account.Quote;
import forex.astronom.Time;
import forex.astronom.planet.Planet;
import forex.astronom.planet.SolarSystem;
import forex.astronom.util.Zodiac;

public class MoonTrader implements Advisor {

  private final SolarSystem SYSTEM = new SolarSystem();
  private final Planet EARTH = SYSTEM.getPlanet(SolarSystem.EARTH);
  private final Planet MOON = SYSTEM.getPlanet(SolarSystem.MOON);
  private final Planet SUN = SYSTEM.getPlanet(SolarSystem.SUN);

  private int COMBO = -1;
  private int THRESHOLD = 110;
  private int VOID_PERIOD = 25200000;

  private long weekStart = 0;
  private long weekEnd = 0;
  private ArrayList<TradeWindow> tradeWindows = new ArrayList<TradeWindow>();

  private TradeWindow currentTradeWindow = null;
  private boolean isTransactionOpen = false;

  public void setCombo(int combo) {
    COMBO = combo;
  }

  public void setThreshold(int threshold) {
    THRESHOLD = threshold;
  }

  public void setVoidPeriod(int voidPeriod) {
    VOID_PERIOD = voidPeriod;
  }

  public List<TradeWindow> getTradeWindows() {
    return tradeWindows;
  }

  @Override
  public Advice getAdvice(Quote quote) {
    Advice advice = Advice.HOLD;
    TradeWindow window = getTradeWindow(quote.getTime());

    if ((window != null) && (window == currentTradeWindow)) {
      if (!isTransactionOpen && isQuoteTradeableInWindow(quote, window)) {
        if (quote.getBuyPips() > window.getStartPips() + THRESHOLD) {
          isTransactionOpen = true;
          advice = new Advice(Advice.Action.Buy, 1000);
          advice.setProperty("window", window.toString());
        } else if (quote.getBuyPips() < window.getStartPips() - THRESHOLD) {
          isTransactionOpen = true;
          advice = new Advice(Advice.Action.Sell, 1000);
          advice.setProperty("window", window.toString());
        }
      } else if (isTransactionOpen && !isQuoteTradeableInWindow(quote, window)) {
        isTransactionOpen = false;
        advice = Advice.CLOSE;
      }
    } else if (isTransactionOpen) {
      isTransactionOpen = false;
      advice = Advice.CLOSE;
    }

    currentTradeWindow = window;
    if ((currentTradeWindow != null) && (currentTradeWindow.getStartPips() == 0)) {
      currentTradeWindow.setStartPips(quote.getBuyPips());
    }
    return advice;
  }

  public TradeWindow getTradeWindow(long time) {
    findTradeWindows(time);

    for (TradeWindow window: tradeWindows) {
      if (window.containsTime(time)) {
        return window;
      }
    }

    return null;
  }

  private synchronized void findTradeWindows(long weekTime) {
    if ((weekStart <= weekTime) && (weekTime <= weekEnd)) {
      return;
    } else {
      tradeWindows.clear();
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
    long windowOpenTime = 0;
    while (time.getTimeInMillis() < weekEnd) {
      time.add(Time.HOUR, 1);

      int newCombo = calculateCombo(time);
      if (newCombo != windowCombo) {
        if (windowOpenTime > 0) {
          tradeWindows.add(new TradeWindow(windowCombo, windowOpenTime, time.getTimeInMillis()));
        }
        windowOpenTime = time.getTimeInMillis();
        windowCombo = newCombo;
      }
    }

    // expand week time bound to include weekends so that trade window in not recalculated for weekends
    weekStart -= 1000L * 60 * 60 * 22; // Sunday
    weekStart -= 1000L * 60 * 60 * 24; // Saturday
    weekStart -= 1000L * 60 * 60 * 2; // Friday
    weekEnd += 1000L * 60 * 60 * 2; // Friday
    weekEnd += 1000L * 60 * 60 * 24; // Saturday
    weekEnd += 1000L * 60 * 60 * 22; // Saturday
  }

  private boolean isQuoteTradeableInWindow(Quote quote, TradeWindow window) {
    if ((COMBO > 0) && (window.getCombo() != COMBO)) {
      return false;
    }

    return window.isInWatchPeriod(quote.getTime()) && (window.getEndTime() - quote.getTime() > VOID_PERIOD);

//    // check for London trading and New York closed
//    Time time = new Time(quote.getTime());
//    if ((time.get(Time.HOUR_OF_DAY) < 8) || (time.get(Time.HOUR_OF_DAY) > 13)) {
//      return false;
//    }
//
//    return true;
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