package telran.probes;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import telran.probes.model.SensorEmailDoc;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorEmailRepo;
import telran.probes.repo.SensorRangeRepo;

@Component
@RequiredArgsConstructor
public class SensorsDbCreation {
 final SensorRangeRepo sensorRangeRepo;
 final SensorEmailRepo sensorEmailRepo;
 /**********************************/
 final static long SENSOR_ID1 = 123l;
 final static long SENSOR_ID2 = 124l;
 final static long SENSOR_ID3 = 125l;
 /**********************************/
 final static float MIN_1 = 0;
 final static float MIN_2 = -10;
 final static float MIN_3 = 10;
 /**********************************/
 final static float MAX_1 = 10;
 final static float MAX_2 = 0;
 final static float MAX_3 = 20;
 /***********************************/
 final static String[] EMAILS_1 = {"admin@gmail.com", "user1@gmail.com"};
 final static String[] EMAILS_2 = {"admin@gmail.com", "user2@gmail.com"};
 final static String[] EMAILS_3 = { "user1@gmail.com"};
 /***********************************/
  final static SensorRangeDoc[] sensorsRange = {
		  new SensorRangeDoc(SENSOR_ID1, MIN_1, MAX_1),
		  new SensorRangeDoc(SENSOR_ID2, MIN_2, MAX_2),
		  new SensorRangeDoc(SENSOR_ID3, MIN_3, MAX_3)
  };
  /**********************************/
  final static SensorEmailDoc[] sensorsEmails = {
		  new SensorEmailDoc(SENSOR_ID1, EMAILS_1),
		  new SensorEmailDoc(SENSOR_ID2, EMAILS_2),
		  new SensorEmailDoc(SENSOR_ID3, EMAILS_3)
  };
  /**********************************/
  public void createDb () {
	  sensorRangeRepo.deleteAll();
	  sensorEmailRepo.deleteAll();
	  List<SensorRangeDoc> listSensorsRange = List.of(sensorsRange);
	  List<SensorEmailDoc> listSensorsEmails = List.of(sensorsEmails);
	  sensorRangeRepo.saveAll(listSensorsRange);
	  sensorEmailRepo.saveAll(listSensorsEmails);
  }
}
