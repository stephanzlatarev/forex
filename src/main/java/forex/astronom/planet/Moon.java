package forex.astronom.planet;

import forex.astronom.util.*;

public class Moon extends Planet {

  private double position;

  public Moon() {
  }

  public Moon(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.MOON;
  }

	public String getIcon() {
		return "<path d='M-6 -9 A10 10 0 0 1 6 9 A5 10 -25 1 0 -6 -9' style='stroke:yellow;stroke-width:3;fill:none' />";
	}

  public boolean position(double t) {
    double l;
    double t2 = t * t;
    double da = t * 36525;
    double ln = Zodiac.degree(259.1833 - 0.05295392 * da + (0.000002 * t + 0.002078) * t2);
    double ms = Zodiac.degree(279.69668 + 36000.7689 * t + 0.0003025 * t2);
    double de = Zodiac.degree(350.737486 + 445267.114 * t - 0.001436 * t2);
    double lp = Zodiac.degree(334.32956 + 0.11140408 * da + (-0.000012 * t + 0.010325) * t2);
    double ma = Zodiac.degree(358.47584 + 35999.04975 * t - 0.00015 * t2);
    double ml = Zodiac.degree(270.434164 + 13.1763965 * da + (0.0000019 * t - 0.001133) * t2);
    double nu = (-(17.2327 + 0.01737 * t) * FNsin(ln) - 1.273 * FNsin(2 * ms)) / 3600;
    double el = Zodiac.degree(296.1046083 + 477198.849 * t + 0.00919167 * t * t);

    // Exact moon calculation starts here
    el = ma;
    double ll = ml - lp;
    double ff = ml - ln;

    //Longterm lunar perturbations coefficients
    double w = FNsin(51.2 + 20.2 * t);
    double x = FNsin(193.4404 - 132.87 * t - 0.0091731 * t2) * 14.27;
    double y = FNsin(ln);
    double z = -15.58 * FNsin(ln + 275.05 - 2.3 * t);

    //Correction to elements
    ml = (0.84 * w + x + 7.261 * y) / 3600 + ml;
    ll = (2.94 * w + x + 9.337 * y) / 3600 + ll;
    de = (7.24 * w + x + 7.261 * y) / 3600 + de;
    el = -6.40 * w / 3600 + el;
    ff = (0.21 * w + x - 88.699 * y - 15.58 * z) / 3600 + ff;

    //Short term lunar perturbations
    l = 22639.55 * FNsin(ll) - 4586.47 * FNsin(ll - 2 * de) + 2369.912 * FNsin(2 * de);
    l = l + 769.02 * FNsin(2 * ll) - 668.15 * FNsin(el);
    l = l - 411.61 * FNsin(2 * ff) - 211.66 * FNsin(2 * ll - 2 * de) - 205.96 * FNsin(ll + el - 2 * de);
    l = l + 191.95 * FNsin(ll + 2 * de) - 165.15 * FNsin(el - 2 * de);
    l = l + 147.69 * FNsin(ll - el) - 125.15 * FNsin(de) - 109.67 * FNsin(ll + el);
    l = l - 55.17 * FNsin(2 * ff - 2 * de) - 45.099 * FNsin(ll + 2 * ff);
    l = l + 39.53 * FNsin(ll - 2 * ff) - 38.43 * FNsin(ll - 4 * de) + 36.12 * FNsin(3 * ll);
    l = l - 30.77 * FNsin(2 * ll - 4 * de) + 28.48 * FNsin(ll - el - 2 * de);
    l = l - 24.42 * FNsin(el + 2 * de);
    l = l + 18.61 * FNsin(ll - de) + 18.02 * FNsin(el + de) + 14.58 * FNsin(ll - el + 2 * de);
    l = l + 14.39 * FNsin(2 * ll + 2 * de) + 13.9 * FNsin(4 * de) - 13.19 * FNsin(3 * ll - 2 * de);
    l = l + 9.7 * FNsin(2 * ll - el) + 9.37 * FNsin(ll - 2 * ff - 2 * de) - 8.63 * FNsin(2 * ll + el - 2 * de);
    l = l - 8.47 * FNsin(ll + de) - 8.096 * FNsin(2 * el - 2 * de) - 7.65 * FNsin(2 * ll + el);
    l = l - 7.49 * FNsin(2 * el) - 7.41 * FNsin(ll + 2 * el - 2 * de) - 6.38 * FNsin(ll - 2 * ff + 2 * de);
    l = l - 5.74 * FNsin(2 * ff + 2 * de) - 4.39 * FNsin(ll + el - 4 * de) - 3.99 * FNsin(2 * ll + 2 * ff);
    l = l + 3.22 * FNsin(ll - 3 * de) - 2.92 * FNsin(ll + el + 2 * de) - 2.74 * FNsin(2 * ll + el - 4 * de);
    l = l - 2.49 * FNsin(2 * ll - el - 2 * de) + 2.58 * FNsin(ll - 2 * el) + 2.53 * FNsin(ll - 2 * el - 2 * de);
    l = l - 2.15 * FNsin(el + 2 * ff - 2 * de) + 1.98 * FNsin(ll + 4 * de) + 1.94 * FNsin(4 * ll);
    l = l - 1.88 * FNsin(el - 4 * de) + 1.75 * FNsin(2 * ll - de) - 1.44 * FNsin(el - 2 * ff + 2 * de);
    l = l - 1.298 * FNsin(2 * ll - 2 * ff) - 1.27 * FNsin(ll + el + de) + 1.23 * FNsin(2 * ll - 3 * de);
    l = l - 1.19 * FNsin(3 * ll - 4 * de) + 1.18 * FNsin(2 * ll - el + 2 * de) - 1.17 * FNsin(ll + 2 * el);
    l = l - 1.09 * FNsin(ll - el - de) + 1.06 * FNsin(3 * ll + 2 * de) - 0.59 * FNsin(2 * ll + de);
    l = l - 0.99 * FNsin(ll + 2 * ff + 2 * de) - 0.95 * FNsin(4 * ll - 2 * de) - 0.57 * FNsin(2 * ll - 6 * de);
    l = l + 0.64 * FNsin(ll - 4 * de) + 0.56 * FNsin(el - de) + 0.76 * FNsin(ll - 2 * el + 2 * de);
    l = l + 0.58 * FNsin(2 * ff - de) - 0.55 * FNsin(3 * ll + el) + 0.68 * FNsin(3 * ll - el);
    l = (l + 0.557 * FNsin(2 * ll + 2 * ff - 2 * de) + 0.538 * FNsin(2 * ll - 2 * ff - 2 * de)) / 3600;

    position = Zodiac.degree(ml + l + nu);
    positioned = true;
    return true;
  }

  public double positionAround(Planet planet) {
    return position;
  }

  private double FNsin(double a) {
    return Math.sin(Math.PI / 180 * a);
  }
}