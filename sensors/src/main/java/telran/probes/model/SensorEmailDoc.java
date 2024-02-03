package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "sensor-emails")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class SensorEmailDoc {
	@Id
	long sensorId;
	String[] emails;

}
