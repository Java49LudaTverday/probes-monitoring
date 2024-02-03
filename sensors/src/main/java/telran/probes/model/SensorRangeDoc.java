package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(collection = "sensor-ranges")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class SensorRangeDoc {
	@Id
	long sensorId;
	float minValue;
	float maxValue;
}
