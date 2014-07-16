package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Uranus extends Planet {

  public Uranus() {
    mL = new Latia(74.1757, 427.2742, 0);
    eL = new Latia(.04682, .00042, 0);
    au = 19.2215;
    apL = new Latia(95.6863, 2.0508, 0);
    anL = new Latia(73.5222, .5242, 0);
    i_nL = new Latia(.7726, .1E-3, 0);
  }

  public Uranus(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.URANUS;
  }

	public String getIcon() {
		return "<g style='stroke:cyan;stroke-width:3;fill:none'><path d='M-10 -10 A6 7 0 1 1 -10 4' /><path d='M10 -10 A6 7 0 1 0 10 4' /><line x1='-4' y1='-3' x2='4' y2='-3' /><line x1='0' y1='-8' x2='0' y2='3' /><circle cx='0' cy='7' r='3' /></g>";
	}

}