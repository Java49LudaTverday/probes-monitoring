package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.probes.dto.SensorEmailsDto;

@Document(collection="sensor-emails")
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SensorEmailsDoc {
	@Id
	long sensorId;
	String[] emails;
	
	public SensorEmailsDoc (SensorEmailsDto sensorEmailsDto) {
		this.sensorId = sensorEmailsDto.sensorId();
		this.emails = sensorEmailsDto.emails();
	}
}