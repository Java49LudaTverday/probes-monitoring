package telran.probes.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SensorBaseRepo<T, ID> extends MongoRepository<T, Long> {
	 Optional<T> findById(Long id);
}
