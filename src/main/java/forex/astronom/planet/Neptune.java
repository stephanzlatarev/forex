package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Neptune extends Planet {

  public Neptune() {
    mL = new Latia(30.13294, 240.45516, 0);
    eL = new Latia(.00913, -.00127, 0);
    au = 30.11375;
    apL = new Latia(284.1683, -21.6329, 0);
    anL = new Latia(130.68415, 1.1005, 0);
    i_nL = new Latia(1.7794, -.0098, 0);
  }

  public Neptune(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.NEPTUNE;
  }

	public String getIcon() {
		return "<g style='stroke:teal;stroke-width:3;fill:none'><path d='M-8 -10 A8 11 0 1 0 8 -10' /><line x1='0' y1='-8' x2='0' y2='10' /><line x1='-4' y1='6' x2='4' y2='6' /></g>";
	}

}