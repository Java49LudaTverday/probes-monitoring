package telran.probes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Configuration
@Getter
public class EmailProviderConfiguration {
	@Value("${app.sensor.email.provider.host}")
	String host;
	@Value("${app.sensor.email.provider.port}")
	int port;
	@Value("${app.sensor.email.provider.url}")
	String url;
	@Value("${app.sensor.email.provider.default.email}")
	String[] defaultEmails;

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
