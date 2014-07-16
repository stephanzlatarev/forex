package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Venus extends Planet {

  public Venus() {
    mL = new Latia(212.6032, 58517.8039, 0.0013);
    eL = new Latia(0.00682, -5.0000000000000002E-005D, 0);
    au = 0.7233;
    apL = new Latia(54.3842, 0.5082, -0.0014);
    anL = new Latia(75.7796, 0.8999, 0.0004);
    i_nL = new Latia(3.3936, 0.001, 0);
  }

  public Venus(PlanetSystem planetsystem) {
    this();
    system = planetsystem;
  }

  public String getName() {
  	return SolarSystem.VENUS;
  }

	public String getIcon() {
		return "<circle r='6' cy='-4' style='stroke:green;stroke-width:3;fill:none' /><line x1='0' y1='3' x2='0' y2='10' style='stroke:green;stroke-width:3' /><line x1='-4' y1='6' x2='4' y2='6' style='stroke:green;stroke-width:3' />";
	}

}