package telran.probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.probes.model.SensorDoc;


public interface SensorRepo extends MongoRepository<SensorDoc, Long> {


}
