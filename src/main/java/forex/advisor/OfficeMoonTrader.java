package forex.advisor;

import forex.account.Quote;
import forex.astronom.Time;
import forex.astronom.planet.Planet;
import forex.astronom.planet.SolarSystem;
import forex.astronom.util.Zodiac;

public class OfficeMoonTrader implements Advisor {

  private int LIMIT = 500;

//  private int COMBO = -1;
//  private int VOID_PERIOD = 25200000;
//
//  private long weekStart = 0;
//  private long weekEnd = 0;
//  private ArrayList<TradeWindow> tradeWindows = new ArrayList<TradeWindow>();
//
//  private TradeWindow currentTradeWindow = null;
//  private boolean isTransactionOpen = false;

  private final int OFFICE_HOURS_START = 8;
  private final int OFFICE_HOURS_MIDS = 13;
  private final int OFFICE_HOURS_END = 21;

  private Quote markQuote = null;
  private int markCombo = -1;
  //private long markRunway = -1;
  private Advice openPosition = null;
  private Quote openQuote = null;

  public void setLimit(int limit) {
    LIMIT = limit;
  }

//  public void setCombo(int combo) {
//    COMBO = combo;
//  }

//  public void setVoidPeriod(int voidPeriod) {
//    VOID_PERIOD = voidPeriod;
//  }
//
//  public List<TradeWindow> getTradeWindows() {
//    return tradeWindows;
//  }

  @Override
  public Advice getAdvice(Quote quote) {
    Time time = new Time(quote.getTime());

//    if (time.get(Time.MONTH) == Time.FEBRUARY) { System.exit(0); }

    if (isFirstOfficeHour(time)) {
      markQuote = quote;
      markCombo = Combo.calculate(time);
      //markRunway = calculateRunway(time);
//    } else if (isOfficeHoursStart(time)) {
//      markQuote = null;
//
//      if (openPosition != null) {
//        openPosition = null;
////        System.out.println(" close " + time + " next window reached");
//        return Advice.CLOSE;
//      }
    } else if ((markQuote != null) && (openPosition == null)) {
      if (isPastMidsOfficeHours(time)) return Advice.HOLD;
      if (markCombo != Combo.calculate(time)) return Advice.HOLD; // No opening new positions when combo switched during trading hours
      //if (quote.getTime() > markQuote.getTime() + markRunway) return Advice.HOLD;

      if (quote.getLowPips() > markQuote.getHighPips()) {
//        System.out.println(" open " + time + " buy");
//        System.out.println("      mark: " + markQuote.getLowPips() + "/" + markQuote.getHighPips() + " " + new Time(markQuote.getTime()));
//        System.out.println("      buy: " + quote.getBuyPips() + " (" + quote.getLowPips() + "/" + quote.getHighPips() + ") " + new Time(quote.getTime()));
        openQuote = quote;
        return openPosition = Advice.BUY;
      } else if (quote.getHighPips() < markQuote.getLowPips()) {
//        System.out.println(" open " + time + " sell");
        openQuote = quote;
        return openPosition = Advice.SELL;
      }
    } else if (openPosition != null) {
      boolean shouldClose = false;

      if ((openPosition == Advice.BUY) && ((quote.getHighPips()*10 - openQuote.getBuyPips()) >= LIMIT)) {
//        System.out.println(" close " + time + " buy limit reached");
        shouldClose = true;
      }
      if ((openPosition == Advice.BUY) && ((openQuote.getBuyPips() - quote.getLowPips()*10) >= LIMIT)) {
//        System.out.println(" close " + time + " buy stop loss");
        shouldClose = true;
      }
      if ((openPosition == Advice.SELL) && ((openQuote.getSellPips() - quote.getLowPips()*10) >= LIMIT)) {
//        System.out.println(" close " + time + " sell limit reached");
        shouldClose = true;
      }
      if ((openPosition == Advice.SELL) && ((quote.getHighPips()*10 - openQuote.getSellPips()) >= LIMIT)) {
//        System.out.println(" close " + time + " sell stop loss");
        shouldClose = true;
      }
      if (Combo.calculate(time) != markCombo) {
//        System.out.println(" close " + time + " combo changed");
        shouldClose = true;
      }
      if (isLastOfficeHourForWeek(time)) {
        shouldClose = true;
      }

      if (shouldClose) {
        openPosition = null;
        markQuote = null;
        return Advice.CLOSE;
      }
    }

    return Advice.HOLD;
  }

  private boolean isFirstOfficeHour(Time time) {
    return time.get(Time.HOUR_OF_DAY) == OFFICE_HOURS_START + 1;
  }

  private boolean isLastOfficeHourForWeek(Time time) {
    return (time.get(Time.DAY_OF_WEEK) == Time.FRIDAY) && (time.get(Time.HOUR_OF_DAY) == OFFICE_HOURS_END);
  }

  private boolean isPastMidsOfficeHours(Time time) {
    return (time.get(Time.HOUR_OF_DAY) > OFFICE_HOURS_MIDS) || (time.get(Time.HOUR_OF_DAY) <= OFFICE_HOURS_START);
  }

//  private long calculateRunway(Time time) {
//    SYSTEM.calculate(time);
//
//    double moonSign = MOON.positionAround(EARTH);
//    double moonSun = MOON.positionAround(EARTH) - SUN.positionAround(EARTH);
//
//    return Math.min(runway(moonSign), runway(moonSun)) * 60 * 60 * 1000;
//  }

  private int runway(double degrees) {
    return degrees2hours(30 - (Zodiac.degree(degrees) % 30));
  }

  private int degrees2hours(double degrees) {
    return (int) (degrees * 2);
  }

}