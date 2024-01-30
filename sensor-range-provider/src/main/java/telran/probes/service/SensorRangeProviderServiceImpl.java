package telran.probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.SensorRange;
import telran.probes.exceptions.SensorNotFoundException;
import telran.probes.model.SensorDoc;
import telran.probes.repo.SensorRepo;

@Service
@Slf4j
@RequiredArgsConstructor
public class SensorRangeProviderServiceImpl implements SensorRangeProviderService {

	final SensorRepo sensorRepo;

	@Override
	public SensorRange getRangeSensor(long id) {
		SensorDoc sensorDoc = sensorRepo.findById(id).orElseThrow(() -> {
			throw new SensorNotFoundException(String.format("sensor %d not exist", id));
		});
		SensorRange res = new SensorRange(sensorDoc.getMinValue(), sensorDoc.getMaxValue());
		log.debug("sensor {} gets range {}", id, res);
		return res;
	}

}
