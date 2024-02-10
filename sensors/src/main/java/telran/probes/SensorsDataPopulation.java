package telran.probes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.configuration.SensorData;
import telran.probes.configuration.SensorsConfiguration;
import telran.probes.model.SensorEmailDoc;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorEmailRepo;
import telran.probes.repo.SensorRangeRepo;

@Component
@RequiredArgsConstructor
@Slf4j
public class SensorsDataPopulation {
final SensorEmailRepo sensorEmailsRepo;
final SensorRangeRepo sensorRangeRepo;
final SensorsConfiguration sensorsConfiguration;
@PostConstruct
void dbPopulation() {
	Map<Long, SensorData> sensorDataMap = sensorsConfiguration.getSensorsDataMap();
	List<SensorEmailDoc> sensorsEmails = sensorEmailsRepo.findAll();
	List<SensorRangeDoc> sensorsRanges = sensorRangeRepo.findAll();
	if(sensorsEmails.isEmpty()) {
		populateEmails(sensorDataMap);
	} else {
		log.debug("Collection  sensors-emails already contains {} documents", sensorsEmails.size());
	}
	if (sensorsRanges.isEmpty()) {
		populateRanges(sensorDataMap);
	} else {
		log.debug("Collection  sensors-ranges already contains {} documents", sensorsRanges.size());
	}
	
}
private void populateRanges(Map<Long, SensorData> sensorDataMap) {
	List<SensorRangeDoc> documents = sensorDataMap.entrySet().stream()
			.map(e -> new SensorRangeDoc(e.getKey(), e.getValue().minValue(),
					e.getValue().maxValue())).toList();
	sensorRangeRepo.saveAll(documents);
	log.debug("{} has been saved", documents.size());
	
	
}
private void populateEmails(Map<Long, SensorData> sensorDataMap) {
	List<SensorEmailDoc> documents = sensorDataMap.entrySet().stream()
			.map(e -> new SensorEmailDoc(e.getKey(), e.getValue().emails())).toList();
	sensorEmailsRepo.saveAll(documents);
	log.debug("{} has been saved", documents.size());
	
}
}