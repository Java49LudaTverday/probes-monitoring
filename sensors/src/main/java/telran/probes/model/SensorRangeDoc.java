package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection = "sensor_ranges")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SensorRangeDoc {
	@Id
	long sensorId;
	float minValue;
	float maxValue;
}
