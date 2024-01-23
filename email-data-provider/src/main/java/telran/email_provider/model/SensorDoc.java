package telran.email_provider.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import telran.probes.dto.EmailData;
import telran.probes.dto.Range;


@Document(collection="sensors")
@AllArgsConstructor
@Getter
public class SensorDoc {
	@Id
    long id;
	Range values;
	List<EmailData> persons;
}
