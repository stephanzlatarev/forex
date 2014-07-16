package forex.astronom.util;

/**
 *  Formulae of the type:
 *
 *  A1 + (A2 * t) + (A3 * t * t)
 */
public class Latia {

  double l1 = 0.0;
  double l2 = 0.0;
  double l3 = 0.0;

  public Latia(double l1, double l2, double l3) {
    this.l1 = l1;
    this.l2 = l2;
    this.l3 = l3;
  }

  public double plain(double time) {
    return l1 + l2 * time + l3 * time * time;
  }

  public double radians(double time) {
    return Trigonometry.radians(Zodiac.degree(plain(time)));
  }

  public double osc(double time, Latia le) {
    double m = radians(time);
    double e = le.plain(time);

    double ea = m;

    for (int a = 1; a < 5; a++) {
      ea = m + e * Math.sin(ea);
    }

    return ea;
  }

}