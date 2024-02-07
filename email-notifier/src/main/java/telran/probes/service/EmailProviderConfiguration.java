package telran.probes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Configuration
@Getter
public class EmailProviderConfiguration {
	@Value("${app.sensor.email.provider.host:localhost}")
	String host;
	@Value("${app.sensor.email.provider.port:8082}")
	int port;
	@Value("${app.sensor.email.provider.url:/sensor/email}")
	String url;
	@Value("${app.sensor.email.provider.default.email:ludachka22@gmail.com}")
	String[] defualtEmails;

	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
