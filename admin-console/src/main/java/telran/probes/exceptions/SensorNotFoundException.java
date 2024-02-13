package telran.probes.exceptions;


import telran.exceptions.NotFoundException;
import telran.probes.api.ErrorMessages;

@SuppressWarnings("serial")
public class SensorNotFoundException extends NotFoundException{

	public SensorNotFoundException() {
		super(ErrorMessages.SENSOR_NOT_FOUND);
		
	}

}
