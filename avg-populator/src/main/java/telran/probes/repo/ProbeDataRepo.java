package telran.probes.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.probes.model.*;
import org.bson.types.ObjectId;
public interface ProbeDataRepo extends MongoRepository<ProbeDataDoc, ObjectId> {

}
