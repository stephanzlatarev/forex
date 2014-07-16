package forex.astronom;

import java.util.Calendar;

public abstract class ActivePoint {

//	private Event birth;
//
//	public void initialize(Event birth) {
//		this.birth = birth;
//	}

	public abstract String getIcon();

	public abstract String getName();

	public abstract double getPosition(Calendar calendar);

//	protected Event getBirth() {
//		return birth;
//	}

}
