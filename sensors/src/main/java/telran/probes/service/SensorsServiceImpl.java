package telran.probes.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.ProbeData;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorEmailRepo;
import telran.probes.repo.SensorRangeRepo;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SensorsServiceImpl implements SensorsService {
	final SensorRangeRepo sensorRangeRepo;
	final SensorEmailRepo sensorEmailRepo;
	final StreamBridge streamBridge;
	@Value("${app.sensor.value.binding.name:sensor_value-out-0}")
	private String sensorValueBindingName;
	private final static Random RANDOM = new Random();

	@Override
	@Scheduled(fixedDelayString = "${app.sensors.fixedDelay.in.millis:1000}")
	public void runPeriodicTask() {
		List<SensorRangeDoc> sensors = sensorRangeRepo.findAll();
		log.trace("gets rensors: {}", sensors);
		sensors.forEach(this:: sensorsProcessing);

	}
	private void sensorsProcessing (SensorRangeDoc sensor) {
		float minValue = sensor.getMinValue();
		float maxValue = sensor.getMaxValue();
		float currentValue = getRandomCurrentValue(minValue, maxValue);
		ProbeData currentProbeData  = new ProbeData(sensor.getSensorId(), currentValue, 
				System.currentTimeMillis());
		log.debug("send probe value: {}", currentProbeData);
		streamBridge.send(sensorValueBindingName, currentProbeData);
	}
	private float getRandomCurrentValue(float minValue, float maxValue) {
		float res = RANDOM.nextFloat(minValue, maxValue + 1);
		return res;
//		return (float) ((Math.random() * (maxValue + 1 - minValue)) + minValue); ;
	}

}
