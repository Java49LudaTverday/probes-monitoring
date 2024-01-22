package telran.sensor.range_provider.repo;

import telran.sensor.range_provider.dto.Range;
import telran.sensor.range_provider.model.SensorDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SensorRepo extends MongoRepository<SensorDoc, Long> {
	
	SensorDoc findById(long id);

}
