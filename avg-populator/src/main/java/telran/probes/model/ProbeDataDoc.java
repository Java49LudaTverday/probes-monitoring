package telran.probes.model;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import telran.probes.dto.ProbeData;

@Document(collection="probe_values")
@ToString
@Getter
@NoArgsConstructor
public class ProbeDataDoc {
Long sensorId;
Float value;
LocalDateTime timestamp;
public ProbeDataDoc(ProbeData probeData) {
	 sensorId = probeData.sensorId();
	 Instant instant = Instant.ofEpochMilli(probeData.timestamp());
	 timestamp = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
	 value = probeData.value();
}
}
