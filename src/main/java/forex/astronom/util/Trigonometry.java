package forex.astronom.util;

/**
 *  Trigonemtry functions.
 */
public class Trigonometry {

  /**
   *  One radian in degrees.
   */
  public static final double RAD_IN_DEG = Math.PI / 180;

  /**
   *  One degrees in radians.
   */
  public static final double DEG_IN_RAD = 180 / Math.PI;

  /**
   *  Calculates arcus sinus.
   *
   * @param  radians  angle in radians
   *
   * @return  asin of the angle
   */
  public static final double asn(double radians) {
    return Math.atan(radians / Math.sqrt(1 - radians * radians));
  }

  /**
   *  Calculates arcus cosinus.
   *
   * @param  radians  angle in radians
   *
   * @return  acos of the angle
   */
  public static final double acs(double radians) {
    return Math.atan(Math.sqrt(1 - radians * radians) / radians);
  }

  /**
   *  Calculates radians from degrees.
   *
   * @param  degrees  angle in degrees
   *
   * @return  angle in radians
   */
  public static final double radians(double degrees) {
    return RAD_IN_DEG * degrees;
  }

  /**
   *  Calculates degrees from radians.
   *
   * @param  radians  angle in radians
   *
   * @return  angle in degrees
   */
  public static final double degrees(double radians) {
    return radians * DEG_IN_RAD;
  }

  /**
   *  Calculates polar angle of coord(x, y).
   *
   * @param  x  x-coordinate
   * @param  y  y-coordinate
   *
   * @return  angle in radians
   */
  public static final double polarAngle(double x, double y) {
    if (y == 0) {
      y = 1.7E-05;
    }

    double a = Math.atan(y / x);

    if (a < 0) {
      a = a + Math.PI;
    }

    if (y < 0) {
      a = a + Math.PI;
    }

    return a;
  }

  /**
   *  Calculates polar radius of coord(x, y).
   *
   * @param  x  x-coordinate
   * @param  y  y-coordinate
   *
   * @return  radius
   */
  public static final double polarRadius(double x, double y) {
    return Math.sqrt(x * x + y * y);
  }

}