package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Saturn extends Planet {

  public Saturn() {
    mL = new Latia(174.2153, 1223.50796, 0);
    eL = new Latia(.05423, -.2E-3, 0);
    au = 9.5525;
    apL = new Latia(338.9117, -.3167, 0);
    anL = new Latia(112.8261, .8259, 0);
    i_nL = new Latia(2.4908, -.0047, 0);
  }

  public Saturn(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.SATURN;
  }

	public String getIcon() {
		return "<g style='stroke:darkolivegreen;stroke-width:3;fill:none'><path d='M-4 -10 L-4 -2 A7 7 0 0 1 6 9' /><line x1='-10' y1='-6' x2='2' y2='-6' /></g>";
	}

}