package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.probes.dto.SensorRange;
import telran.probes.dto.SensorRangeDto;

@Document(collection = "sensor-ranges")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class SensorRangeDoc {
	@Id
	long id;
	float minValue;
	float maxValue;

	public static SensorRangeDoc of(SensorRangeDto sensorRangeDto) {
		SensorRange sensorRange = sensorRangeDto.sensorRange();
		return new SensorRangeDoc(sensorRangeDto.sensorId(), sensorRange.minValue(),
				sensorRange.maxValue());
	}
	public SensorRangeDto build() {
		return new SensorRangeDto(id, new SensorRange(minValue, maxValue));
	}
}
