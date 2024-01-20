package telran.probes.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection="probe_values")
@AllArgsConstructor
@ToString
@Getter
public class ProbeDataDoc {
@Id
Long sensorId;
Float value;
LocalDateTime timestamp;
}
