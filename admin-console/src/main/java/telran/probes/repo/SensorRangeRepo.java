package telran.probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.probes.model.SensorRangeDoc;


public interface SensorRangeRepo extends MongoRepository<SensorRangeDoc, Long> {


}
