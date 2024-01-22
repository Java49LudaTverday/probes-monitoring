package telran.sensor.range_provider.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.sensor.range_provider.dto.Range;
import telran.sensor.range_provider.exceptions.SensorNotFoundException;
import telran.sensor.range_provider.model.SensorDoc;
import telran.sensor.range_provider.repo.SensorRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorRangeProviderServiceImpl implements SensorRangeProviderService {

	final SensorRepo sensorRepo;

	@Override
	public Range getRangeSensor(long id) {
		if (!sensorRepo.existsById(id)) {
			throw new SensorNotFoundException(String.format("sensor %d not exist", id));
		}
		SensorDoc sensorDoc = sensorRepo.findById(id);
		Range res = sensorDoc.getValues();
		log.debug("sensor {} gets range {}", id, res);
		return res;
	}

}
