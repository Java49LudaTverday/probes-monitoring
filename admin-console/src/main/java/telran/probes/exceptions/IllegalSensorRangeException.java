package telran.probes.exceptions;

public class IllegalSensorRangeException extends IllegalStateException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalSensorRangeException () {
		super("max value must be greater than min value");
	}
}
