package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.probes.dto.SensorRange;
import telran.probes.dto.SensorRangeDto;

@Document(collection = "sensors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SensorRangeDoc {
	@Id
	long id;
	float minValue;
	float maxValue;

	public SensorRangeDoc(SensorRangeDto sensorRangeDto) {
		SensorRange range = sensorRangeDto.sensorRange();
		this.id = sensorRangeDto.sensorId();
		this.minValue = range.minValue();
		this.maxValue = range.maxValue();
	}
}
