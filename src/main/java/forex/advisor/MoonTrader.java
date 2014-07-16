package forex.advisor;

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
//  private final MoonNode NODE = new MoonNode();

  private int THRESHOLD = 110;
  private int VOID_PERIOD = 11;
  private long WAIT_PERIOD = 0;

  private int baseCombo = calculateCombo(System.currentTimeMillis());
  private int currentCombo = 0;
  private int voidPeriod = 0;

  private int basePips = 0;
  private long baseTime = 0;

  private boolean isTransactionOpen = false;

  public void setThreshold(int threshold) {
    THRESHOLD = threshold;
  }

  public void setVoidPeriod(int voidPeriod) {
    VOID_PERIOD = voidPeriod;
  }

  public void setWaitPeriod(long waitPeriod) {
    WAIT_PERIOD = waitPeriod;
  }

  @Override
  public Advice getAdvice(Quote quote) {
    calculate(quote);

    if (currentCombo != baseCombo) {
      baseCombo = currentCombo;
      basePips = quote.getBuyPips();
      baseTime = quote.getTime();

      if (isTransactionOpen) {
        isTransactionOpen = false;
        return Advice.CLOSE;
      } else {
        return Advice.HOLD;
      }
    }

    if (quote.getTime() <= baseTime + WAIT_PERIOD) {
      basePips = quote.getBuyPips();
    }

    if (!isTransactionOpen && (quote.getTime() > baseTime + WAIT_PERIOD) && (voidPeriod > VOID_PERIOD)) {
      if (quote.getBuyPips() > basePips + THRESHOLD) {
        isTransactionOpen = true;
        return Advice.BUY;
      }
  
      if (quote.getBuyPips() < basePips - THRESHOLD) {
        isTransactionOpen = true;
        return Advice.SELL;
      }
    }

    if (!isTransactionOpen) {
      System.out.println("[moon trader] next trade in about " + (voidPeriod * 28 * 24 / 360) + " hours");
    }

    return Advice.HOLD;
  }

  private void calculate(Quote quote) {
    calculateCombo(quote.getTime());
  }

  private int calculateCombo(long time) {
    SYSTEM.calculate(new Time(time));

    double moonSign = MOON.positionAround(EARTH);
    double moonSun = MOON.positionAround(EARTH) - SUN.positionAround(EARTH);

//    NODE.position(time.getStandardYearTime());
//    double moonNode = NODE.getPosition(null);

    currentCombo = sign(moonSign) * 100 + sign(moonSun);
    voidPeriod = Math.min(voidPeriod(moonSign), voidPeriod(moonSun));
//    System.out.println(" moon: zodiac: " + moonSign + "; sun: " + moonSunAspect + "; combo: " + currentCombo);

    return currentCombo;
  }

  private final int sign(double degree) {
    return (int) Zodiac.degree(degree) / 30 + 1;
  }

  private final int voidPeriod(double degree) {
    return 30 - (int) Zodiac.degree(degree) % 30;
  }

}