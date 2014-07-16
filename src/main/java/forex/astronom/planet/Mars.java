package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Mars extends Planet {

  public Mars() {
    mL = new Latia(319.5294, 19139.8585, .2E-3);
    eL = new Latia(.09331, .9E-4, 0);
    au = 1.5237;
    apL = new Latia(285.4318, 1.0698, .1E-3);
    anL = new Latia(48.7864, .77099, 0);
    i_nL = new Latia(1.8503, -.7E-3, 0);
  }

  public Mars(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.MARS;
  }

	public String getIcon() {
		return "<g style='stroke:red;stroke-width:3;fill:none'><circle r='8' cx='-2' cy='2' /><line x1='4' y1='-4' x2='10' y2='-10' /><path d='M10 -3 L10 -10 L3 -10' /></g>";
	}

}