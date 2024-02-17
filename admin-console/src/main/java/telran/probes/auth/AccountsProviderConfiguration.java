package telran.probes.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Configuration
@Getter
public class AccountsProviderConfiguration {
	@Value("${app.accounts.provider.host:localhost}")
	String host;
	@Value("${app.accounts.provider.port:8686}")
	int port;
	@Value("${app.accounts.provider.url:/accounts}")
	String url;
	
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
