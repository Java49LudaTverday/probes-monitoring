package telran.sensor.range_provider.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.probes.dto.Range;

@Document(collection="sensors")
@AllArgsConstructor
@Getter
public class SensorDoc {
	@Id
    long id;
	Range values;
}
