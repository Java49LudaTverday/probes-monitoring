package telran.email_provider.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.probes.dto.EmailData;


@Document(collection="sensors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SensorDoc {
	@Id
    long id;
	String[] emails;
}
