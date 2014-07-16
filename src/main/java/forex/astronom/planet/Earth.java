package forex.astronom.planet;

import forex.astronom.util.Latia;

public class Earth extends Planet {

  public Earth() {
    mL = new Latia(358.4758, 35999.0498, -.0002);
    eL = new Latia(.01675, -.4E-4, 0);
    au = 1;
    apL = new Latia(101.2208, 1.7192, .00045);
    anL = new Latia(0, 0, 0);
    i_nL = new Latia(0, 0, 0);
  }

  public Earth(PlanetSystem system) {
    this();
    this.system = system;
  }

  public String getName() {
  	return SolarSystem.EARTH;
  }

}
