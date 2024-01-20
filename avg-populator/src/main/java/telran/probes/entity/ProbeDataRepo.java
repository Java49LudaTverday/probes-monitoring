package telran.probes.entity;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.probes.model.*;

public interface ProbeDataRepo extends MongoRepository<ProbeDataDoc, Long> {

}
