package telran.probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.probes.model.*;

public interface ProbeDataRepo extends MongoRepository<ProbeDataDoc, Long> {

}
