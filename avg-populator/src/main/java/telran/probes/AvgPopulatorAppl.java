package telran.probes;

import java.time.*;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.ProbeData;
import telran.probes.model.ProbeDataDoc;
import telran.probes.repo.ProbeDataRepo;

@SpringBootApplication
@Slf4j
public class AvgPopulatorAppl {
 @Autowired
 ProbeDataRepo probeRepo;
	public static void main(String[] args) {
		SpringApplication.run(AvgPopulatorAppl.class, args);
	}
	
	@Bean
	Consumer<ProbeData> consumerProbeDataPopulator (){
		return this::consumerMethod;
	}

	void consumerMethod(ProbeData probeData) {
		log.debug("received probe: {}", probeData);
		ProbeDataDoc probeDoc = new ProbeDataDoc(probeData); 
		log.debug("probeDataDoc {} has been saved", probeDoc);
		probeRepo.save(probeDoc);
	}

}
