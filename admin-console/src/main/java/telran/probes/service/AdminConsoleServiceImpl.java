package telran.probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.SensorEmailsDto;
import telran.probes.dto.SensorRange;
import telran.probes.dto.SensorRangeDto;
import telran.probes.exceptions.SensorNotFoundException;
import telran.probes.model.SensorEmailsDoc;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorEmailsRepo;
import telran.probes.repo.SensorRangeRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminConsoleServiceImpl implements AdminConsoleService {

	final SensorEmailsRepo sensorEmailsRepo;
	final SensorRangeRepo sensorRangeRepo;
	
	@Override
	public SensorRangeDto updateSensorRange(SensorRangeDto sensorRangeDto) {
		long sensorId = sensorRangeDto.sensorId();
		if(sensorRangeRepo.existsById(sensorId)) {
			new SensorNotFoundException(String.format("sensor with id {} not exist",sensorId ));
		}
		SensorRange sensorRange = sensorRangeDto.sensorRange();
		SensorRangeDoc sensorRangeDoc = new SensorRangeDoc(sensorRangeDto);
		sensorRangeRepo.save(sensorRangeDoc);
		log.debug("new range: {} for sensor: {}  has been saved", sensorRange ,sensorId);
		return sensorRangeDto;
	}

	@Override
	public SensorEmailsDto updateSensorEmails(SensorEmailsDto sensorEmailsDto) {
		long sensorId = sensorEmailsDto.sensorId();
		if(sensorEmailsRepo.existsById(sensorId)) {
			new SensorNotFoundException(String.format("sensor with id {} not exist", sensorId));
		}
		sensorEmailsRepo.save(new SensorEmailsDoc(sensorEmailsDto));
		log.debug("new emails: {} for sensor: {}  has been saved", sensorEmailsDto.emails(), sensorId);
		return sensorEmailsDto;
	}

}
