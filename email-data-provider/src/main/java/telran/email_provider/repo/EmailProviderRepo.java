package telran.email_provider.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.email_provider.model.SensorDoc;

public interface EmailProviderRepo extends MongoRepository<SensorDoc, Long> {

}
