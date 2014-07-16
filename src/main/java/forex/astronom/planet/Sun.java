package forex.astronom.planet;

public class Sun extends Planet {

  public Sun() {
    accordPoint.setCoordinates(0, 0, 0);
    coordinates.setCoordinates(0, 0, 0);

    positioned = true;
  }

  public Sun(PlanetSystem system) {
    this();
    this.system = system;
  }

  public void markToBePositioned() {
  }

  public boolean position(double time) {
    return true;
  }

  public String getName() {
  	return SolarSystem.SUN;
  }

	public String getIcon() {
		return "<g style='stroke:gold;stroke-width:3;fill:none'><circle r='10' /><circle r='1' /></g>";
	}

}