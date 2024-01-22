package telran.sensor.range_provider.test;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.sensor.range_provider.dto.Range;
import telran.sensor.range_provider.dto.Sensor;
import telran.sensor.range_provider.model.SensorDoc;
import telran.sensor.range_provider.repo.SensorRepo;


@Component
@RequiredArgsConstructor
public class DbTestCreation {
	
	final SensorRepo sensorRepo;
	/***************************/
	final static long ID_1 = 100l;
	final static Range RANGE_1 = new Range(0, 100);
    /*************************/
	final static long ID_2 = 200l;
	final static Range RANGE_2 = new Range(-100, 100);
	/***************************/
	final static long ID_3 = 300l;
	final static Range RANGE_3 = new Range(10, 100);
	/***************************/
	Sensor[] sensors = {
			new Sensor(ID_1, RANGE_1),
			new Sensor(ID_2, RANGE_2),
			new Sensor(ID_3, RANGE_3)
	};
	public void createDB () {
		sensorRepo.deleteAll();
		List<SensorDoc> sensorDocs = List.of(sensors).stream()
				.map(s -> new SensorDoc(s.id(), s.range())).toList();
		sensorRepo.saveAll(sensorDocs);

	}
	
}
