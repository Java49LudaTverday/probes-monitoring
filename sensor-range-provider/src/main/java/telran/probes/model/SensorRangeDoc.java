package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection="sensor-ranges")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SensorRangeDoc {
	@Id
    long id;
	float minValue;
	float maxValue;
}
