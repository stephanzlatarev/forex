package forex.astronom.planet;

import java.util.Calendar;

import forex.astronom.ActivePoint;
import forex.astronom.util.*;

public abstract class Planet extends ActivePoint {

  int index;
  public PlanetSystem system;

  double au;
  Latia mL = new Latia(0, 0, 0);
  Latia eL = new Latia(0, 0, 0);
  Latia apL = new Latia(0, 0, 0);
  Latia anL = new Latia(0, 0, 0);
  Latia i_nL = new Latia(0, 0, 0);

  boolean positioned = false;

  TrigonometryPoint coordinates = new TrigonometryPoint();
  TrigonometryPoint accordPoint = new TrigonometryPoint();

  public Planet() {
    markToBePositioned();
  }

  public Planet(PlanetSystem system) {
    this.system = system;
    markToBePositioned();
  }

  public boolean isPositioned() {
    return positioned;
  }

  public void markToBePositioned() {
    positioned = false;
  }

  public boolean position(double time) {
    double e = 0, ea = 0, e1 = 0; // m = 0;
    double ap = 0, an = 0, i_n = 0, x = 0, y = 0; //, r = 0, a = 0;
//    double c = -1;
//    double d;

//    TrigonometryPoint point = new TrigonometryPoint();

//    m = mL.radians(time);

    e = eL.plain(time);
    ap = apL.radians(time);
    an = anL.radians(time);
    i_n = i_nL.radians(time);
    ea = mL.osc(time, eL);

    e1 = 0.01720209 / (Math.pow(au, 1.5) * (1 - e * Math.cos(ea)));

    x = -(au * e1) * Math.sin(ea);
    y = (au * e1) * Math.pow(Math.abs(1 - e * e), 0.5) * Math.cos(ea);

    //rotate;
    accordPoint.setCoordinates(x, y, 0);
    accordPoint.rotate(ap, i_n, an);
    accordPoint.setZ(0);

    x = au * (Math.cos(ea) - e);
    y = au * Math.sin(ea) * Math.pow(Math.abs(1 - e * e), 0.5);

    //rotate;
    coordinates.setCoordinates(x, y, 0);
    coordinates.rotate(ap, i_n, an);

    positioned = true;
    return true;
  }

  // TODO: obsolete
  public double positionAround(Planet planet) {
    Planet sun = planet;
    double xw, yw;
    double xx, yy, zz;
    double xk;

    xw = accordPoint.getX() - sun.accordPoint.getX();
    yw = accordPoint.getY() - sun.accordPoint.getY();
//    zw = accordPoint.getZ() - sun.accordPoint.getZ();

    xx = coordinates.getX() - sun.coordinates.getX();
    yy = coordinates.getY() - sun.coordinates.getY();
    zz = coordinates.getZ() - sun.coordinates.getZ();
    xk = Trigonometry.degrees((xx * yw - yy * xw) / (xx * xx + yy * yy));

    double br = 0.0057683 * Math.sqrt(xx * xx + yy * yy + zz * zz) * xk;

    //spherical i,xx,yy,zz,a,r,br,ab,ss,p,c
    return Zodiac.degree(Trigonometry.degrees(Trigonometry.polarAngle(xx, yy)) + br);
  }

  public double getAu() {
    return au;
  }

  public TrigonometryPoint getCoordinates() {
    return coordinates;
  }

  public double getPosition(Calendar calendar) {
  	return positionAround(new SolarSystem().getPlanet(SolarSystem.EARTH));
  }

	public String getIcon() {
		return null;
	}

}