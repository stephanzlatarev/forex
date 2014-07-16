package forex.astronom;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Time extends GregorianCalendar {

  private final static long serialVersionUID = 7844562673459346321L;

  public final static String[] MONTHS_SHORT = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

  public Time() {
    setTimeZone(TimeZone.getTimeZone("Europe/Sofia"));
    setTimeInMillis(System.currentTimeMillis());
  }

  public Time(Time time) {
    setTimeZone(time.getTimeZone());
    setTimeInMillis(time.getTimeInMillis());
  }

  /**
   * @param text "kk:mm:ss dd-MM-yyyy"
   */
//  public Time(String text, int location) {
//    try {
//      DateFormat dateFormat = new SimpleDateFormat("kk:mm:ss dd-MM-yyyy");
//      TimeZone zone = Location.getLocation(location).getTimeZone();
//      dateFormat.setTimeZone(zone);
//      this.setTimeZone(zone);
//
//      long timestamp = dateFormat.parse(text).getTime();
//      this.setTimeInMillis(timestamp);
//    } catch (NullPointerException e) {
//      this.setTimeInMillis(System.currentTimeMillis());
//    } catch (ParseException e) {
//      e.printStackTrace();
//      this.setTimeInMillis(System.currentTimeMillis());
//    }
//  }

  public Time(Date date) {
    this.setTimeInMillis(date.getTime());
  }

  public Time(long time) {
//    setTimeZone(TimeZone.getTimeZone("Europe/Sofia"));
    setTimeZone(TimeZone.getTimeZone("GMT"));
    this.setTimeInMillis(time);
  }

  public Time(long time, TimeZone zone) {
    this.setTimeInMillis(time);
    this.setTimeZone(zone);
  }

  public double getHourOfDay() {
    return get(HOUR_OF_DAY) + ((double) get(MINUTE)) / 60 + ((double) get(SECOND)) / 3600;
  }

  public double getGMTHourOfDay() {
    Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    gmt.setTimeInMillis(this.getTimeInMillis());
    return gmt.get(HOUR_OF_DAY) + ((double) gmt.get(MINUTE)) / 60 + ((double) gmt.get(SECOND)) / 3600;
  }

  public double getJulianDay() {
    int im = (12 * (get(YEAR) + 4800)) + (get(MONTH) - 2);
    double j = (2 * (im - (im / 12) * 12) + 7 + 365 * im) / 12;
    j = ((int) j) + get(DAY_OF_MONTH) + (im / 48) - 32083;
    if (j > 2299171) {
      j = j + (im / 4800) - (im / 1200) + 38;
    }

    return j;
  }

  public double getGMTJulianDay() {
    Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    gmt.setTimeInMillis(this.getTimeInMillis());
    int im = (12 * (gmt.get(YEAR) + 4800)) + (gmt.get(MONTH) - 2);
    double j = (2 * (im - (im / 12) * 12) + 7 + 365 * im) / 12;
    j = ((int) j) + gmt.get(DAY_OF_MONTH) + (im / 48) - 32083;
    if (j > 2299171) {
      j = j + (im / 4800) - (im / 1200) + 38;
    }

    return j;
  }

  public double getStandardYearTime() {
    return getJulianYearTime();
  }

  public double getJulianYearTime() {
    return ((getGMTJulianDay() - 2415020) + ((getGMTHourOfDay() / 24) - 0.5)) / 36525;
  }

  public String toString() {
    return get(DAY_OF_MONTH) + " " + MONTHS_SHORT[get(MONTH)] + " " + get(YEAR) + " "
            + format(get(HOUR_OF_DAY)) + ":" + format(get(MINUTE)) + ":" + format(get(SECOND)) + " "
            + "[" + getTimeZone().getID() + "]"
            + (getTimeZone().inDaylightTime(this.getTime()) ? " DST" : "");
  }

  public String toSimpleString() {
    return get(DAY_OF_MONTH) + " " + MONTHS_SHORT[get(MONTH)] + " " + get(YEAR) + " "
            + format(get(HOUR_OF_DAY)) + ":" + format(get(MINUTE)) + ":" + format(get(SECOND));
  }

  public String toMySQLString() {
    return get(YEAR) + "-" + (get(MONTH) + 1) + "-" + get(DAY_OF_MONTH) + " "
            + format(get(HOUR_OF_DAY)) + ":" + format(get(MINUTE)) + ":" + format(get(SECOND));
  }

  private final String format(int data) {
    String result = "00" + data;
    return result.substring(result.length() - 2);
  }

}
