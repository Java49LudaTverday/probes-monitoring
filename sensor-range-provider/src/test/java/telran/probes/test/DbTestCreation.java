package telran.probes.test;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.probes.dto.Sensor;
import telran.probes.dto.SensorRange;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorRangeRepo;


@Component
@RequiredArgsConstructor
public class DbTestCreation {
	
	final SensorRangeRepo sensorRepo;
	/***************************/
	final static long ID_1 = 100l;
	final static float MIN_VALUE_1 = 0;
	final static float MAX_VALUE_1 = 100;
	final static SensorRange RANGE_1 = new SensorRange(MIN_VALUE_1, MAX_VALUE_1);
    /*************************/
	final static long ID_2 = 200l;
	final static float MIN_VALUE_2 = -100;
	final static float MAX_VALUE_2 = 100;
	final static SensorRange RANGE_2 = new SensorRange(MIN_VALUE_1, MAX_VALUE_2);
	/***************************/
	final static long ID_3 = 300l;
	final static float MIN_VALUE_3 = 10;
	final static float MAX_VALUE_3 = 100;
	final static SensorRange RANGE_3 = new SensorRange(MIN_VALUE_3, MAX_VALUE_3);
	/***************************/
	Sensor[] sensors = {
			new Sensor(ID_1, MIN_VALUE_1, MAX_VALUE_1),
			new Sensor(ID_2, MIN_VALUE_2, MAX_VALUE_2),
			new Sensor(ID_3, MIN_VALUE_3,  MAX_VALUE_3)
	};
	public void createDB () {
		sensorRepo.deleteAll();
		List<SensorRangeDoc> sensorDocs = List.of(sensors).stream()
				.map(s -> new SensorRangeDoc(s.id(), s.minValue(), s.maxValue())).toList();
		sensorRepo.saveAll(sensorDocs);

	}
	
}
