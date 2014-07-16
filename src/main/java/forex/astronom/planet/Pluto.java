package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Pluto extends Planet {

  public Pluto() {
    mL = new Latia(229.94722, 144.91306, 0);
    eL = new Latia(0.24864, 0, 0);
    au = 39.51774;
    apL = new Latia(113.52139, 0, 0);
    anL = new Latia(108.95444, 1.39601, 0.00031);
    i_nL = new Latia(17.14678, 0, 0);
  }

  public Pluto(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.PLUTO;
  }

	public String getIcon() {
		return "<g style='stroke:firebrick;stroke-width:3;fill:none'><circle cx='0' cy='-7' r='3' /><path d='M-8 -10 A8 11 0 1 0 8 -10' /><line x1='0' y1='2' x2='0' y2='10' /><line x1='-4' y1='6' x2='4' y2='6' /></g>";
	}

}