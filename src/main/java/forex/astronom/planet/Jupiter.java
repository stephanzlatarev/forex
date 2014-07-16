package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Jupiter extends Planet {

  public Jupiter() {
    mL = new Latia(225.4928, 3033.6879, 0);
    eL = new Latia(.04838, -.2E-4, 0);
    au = 5.2029;
    apL = new Latia(273.393, 1.3383, 0);
    anL = new Latia(99.4198, 1.0583, 0);
    i_nL = new Latia(1.3097, -.52E-2, 0);
  }

  public Jupiter(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.JUPITER;
  }

	public String getIcon() {
		return "<g style='stroke:orange;stroke-width:3;fill:none'><path d='M-10 -10 A7 7 0 0 1 -4 5 L10 5' /><line x1='4' y1='0' x2='4' y2='10' /></g>";
	}

}