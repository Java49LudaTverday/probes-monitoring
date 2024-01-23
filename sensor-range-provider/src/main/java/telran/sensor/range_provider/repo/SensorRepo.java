package telran.sensor.range_provider.repo;

import telran.sensor.range_provider.model.SensorDoc;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SensorRepo extends MongoRepository<SensorDoc, Long> {
	
	SensorDoc findById(long id);

}
