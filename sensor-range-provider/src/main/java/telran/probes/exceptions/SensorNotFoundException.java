package telran.probes.exceptions;

import telran.exceptions.NotFoundException;

public class SensorNotFoundException extends NotFoundException{

	private static final long serialVersionUID = 1L;

	public SensorNotFoundException(String message) {
		super(message);
	}
	

}
