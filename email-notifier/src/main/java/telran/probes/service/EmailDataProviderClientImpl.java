package telran.probes.service;

import java.util.HashMap;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailDataProviderClientImpl implements EmailDataProviderClient {
	HashMap<Long, String[]> mapEmails = new HashMap<>();
	@Value("${app.update.message.delimiter:#}")
	private String delimiter;
	@Value("${app.update.token.email:email-update}")
	private String emailUpdateMessage;
	final EmailProviderConfiguration providerConfiguration;
	final RestTemplate restTemplate;

	@Override
	public String[] getEmails(long sensorId) {
		String[] emails = mapEmails.get(sensorId);
		return emails == null ? getEmailsFromService(sensorId) : emails;
	}
	
	@Bean
	Consumer<String> configChangeConsumer () {
		return this::checkConfigurationUpdates;
	}
	
	void checkConfigurationUpdates (String message) {
		String[] tokens = message.split(delimiter);
		if(tokens[0].equals(emailUpdateMessage) ) {
			updateMapEmails(tokens[1]);
		}
	}

	@SuppressWarnings({ "unlikely-arg-type" })
	private void updateMapEmails(String token) {
		long sensorId = Long.parseLong(token);
		if(mapEmails.containsKey(token)) {
			getEmailsFromService(sensorId);
		}		
	}

	private String[] getEmailsFromService(long sensorId)  {
		String[] res = null;
		try {
			ResponseEntity<?> responseEntity = 
					restTemplate.exchange(getFullUrl(sensorId), HttpMethod.GET, null, String.class);
			if(!responseEntity.getStatusCode().is2xxSuccessful()) {
				new Exception((String)responseEntity.getBody());
			}
			res = (String[])responseEntity.getBody();
			mapEmails.put(sensorId, res);
		} catch (Exception e) {
			log.error("no emails provided for sensor {}",sensorId  );
			res[0] = providerConfiguration.defualtEmail;
			log.warn("Taken default email for sensor {}",res[0], sensorId);
		}
		log.debug("Emails for sensor {} is {}", sensorId , res );
		return res;
	}

	private String getFullUrl(long sensorId) {
		String res = String.format("http://%s:%d%s/%d",
				providerConfiguration.getHost(), 
				providerConfiguration.getPort(),
				providerConfiguration.getUrl(),
				sensorId);
		log.debug("url: {}", res);
		return res;
	}

}
