package telran.probes;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import telran.probes.configuration.SensorData;
import telran.probes.model.SensorEmailDoc;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorEmailRepo;
import telran.probes.repo.SensorRangeRepo;

@Component
@RequiredArgsConstructor
public class SensorsDataPopulation {
 
	final static List<SensorRangeDoc> sensorsRanges = List.of( new SensorRangeDoc(123l, 100f, 200f),
			new SensorRangeDoc(124l, -10f, 20f),
			new SensorRangeDoc(125l, -10, 20)) ;
	final static List<SensorEmailDoc> sensorsEmails = List.of( new SensorEmailDoc(123l, new String[] { "ludachka22@gmail.com" }),
			new SensorEmailDoc(124l, new String[] { "ludachka22@gmail.com" }),
			new SensorEmailDoc(125l, new String[] { "ludachka22@gmail.com" })) ;
	
	final SensorEmailRepo sensorsEmailRepo;
	final SensorRangeRepo sensorsRangesRepo;
	
	@PostConstruct
	void initialPopulation () {
		sensorsEmailRepo.deleteAll();
		sensorsRangesRepo.deleteAll();
		sensorsEmailRepo.saveAll(sensorsEmails);
		sensorsRangesRepo.saveAll(sensorsRanges);
	}
}
