package forex.astronom.util;

public class TrigonometryPoint {

  private double x = 0;
  private double y = 0;
  private double z = 0;

  public final double getX() {
    return x;
  }

  public final double getY() {
    return y;
  }

  public final double getZ() {
    return z;
  }

  public final double getPolarAngle() {
    return Trigonometry.polarAngle(x, y);
  }

  public final double getPolarRadius() {
    return Trigonometry.polarRadius(x, y);
  }

  public final void setCoordinates(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;

    if (this.x == 0) {
      this.x = 1.7E-05;
    }

    if (this.y == 0) {
      this.y = 1.7E-05;
    }

    if (this.z == 0) {
      this.z = 1.7E-05;
    }
  }

  public final void setPolarCoordinates(double angle, double radius) {
    if (angle == 0) {
      angle = 1.7E-05;
    }

    if (angle < 0) {
      angle += (2 * Math.PI);
    }

    setCoordinates(radius * Math.cos(angle), radius * Math.sin(angle), z);
  }

  /**
   *  Rotates coord(x, y) by.
   *
   * @param  x  x-coordinate
   * @param  y  y-coordinate
   *
   * @return  radius
   */
  public synchronized final void rotate(double by1, double by2, double by3) {
    setPolarCoordinates(getPolarAngle() + by1, getPolarRadius());

    double d = x;
    x = y;
    y = z;

    setPolarCoordinates(getPolarAngle() + by2, getPolarRadius());

    double g = y;
    y = x;
    x = d;

    setPolarCoordinates(getPolarAngle() + by3, getPolarRadius());

    z = g;
  }

  public final void setX(double x) {
    this.x = x;
  }

  public final void setY(double y) {
    this.y = y;
  }

  public final void setZ(double z) {
    this.z = z;
  }

  public String toString() {
    return "x: " + getX() + " y: " + getY() + " z: " + getZ();
  }
}