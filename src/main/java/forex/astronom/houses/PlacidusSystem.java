package forex.astronom.houses;

import forex.astronom.Time;
import forex.astronom.util.Zodiac;
import forex.astronom.util.Trigonometry;

public class PlacidusSystem extends HouseSystem {

  public PlacidusSystem(Time event) {
    super(event);
  }

  public double getHouse(int number) {
    double ra = getRa();

    switch (number) {
      case 1:
        {
          // ascendant
          return ascendant();
        }

      case 2:
        {
          return Zodiac.degree(placidus(1.5, 1, ra + Trigonometry.radians(120)));
        }

      case 3:
        {
          return Zodiac.degree(placidus(3, 1, ra + Trigonometry.radians(150)));
        }

      case 4:
        {
          return Zodiac.degree(getHouse(10) + 180);
        }

      case 5:
        {
          return Zodiac.degree(placidus(3, 0, ra + Trigonometry.radians(30)) + 180);
        }

      case 6:
        {
          return Zodiac.degree(placidus(1.5, 0, ra + Trigonometry.radians(60)) + 180);
        }

      case 7:
      case 8:
      case 9:
      case 11:
      case 12:
        {
          return Zodiac.degree(getHouse(number - 6) + 180);
        }

      case 10:
        {
          // midheaven
          return midheaven();
        }

      default:
        {
          return 0;
        }
    }
  }

  private double placidus(double ff, double y, double r1) {
    double xx, r2, lo;
    double ra = getRa();
    double ob = getOb();
    double la = Trigonometry.radians(0); // Greenwich lattitude
    int x = (y == 1) ? 1 : -1;

    for (int i = 1; i < 11; i++) {
      xx = Trigonometry.acs(x * Math.sin(r1) * Math.tan(ob) * Math.tan(la));
      if (xx < 0) {
        xx = xx + Math.PI;
      }

      r2 = ra + (xx / ff);
      if (y == 1) {
        r2 = ra + Math.PI - (xx / ff);
      }
      r1 = r2;
    }

    lo = Math.atan(Math.tan(r1) / Math.cos(ob));

    if (lo < 0) {
      lo += Math.PI;
    }

    if (Math.sin(r1) < 0) {
      lo = lo + Math.PI;
    }

    return Trigonometry.degrees(lo);
  }

  private double ascendant() {
    double ra = getRa();
    double ob = getOb();
    double la = Trigonometry.radians(0); // Greenwich lattitude
    double asn = Math.atan(Math.cos(ra) / (-Math.sin(ra) * Math.cos(ob) - Math.tan(la) * Math.sin(ob)));

    if (asn < 0) {
      asn += Math.PI;
    }

    if (Math.cos(ra) < 0) {
      asn += Math.PI;
    }

    return Zodiac.degree(Trigonometry.degrees(asn));
  }

  private double midheaven() {
    double x = Math.atan(Math.tan(getRa()) / Math.cos(getOb()));

    if (x < 0) {
      x += Math.PI;
    }

    if (Math.sin(getRa()) < 0) {
      x += Math.PI;
    }

    return Zodiac.degree(Trigonometry.degrees(x));
  }

  public final double getRa() {
    double t = event.getJulianYearTime();
    return Trigonometry.radians(Zodiac.degree((6.6460656 + (2400.0513 * t) + (2.58E-05 * t * t) + event.getGMTHourOfDay()) * 15));
  }

  public final double getOb() {
    return Trigonometry.radians(23.452294 - 0.0130125 * event.getJulianYearTime());
  }

}
