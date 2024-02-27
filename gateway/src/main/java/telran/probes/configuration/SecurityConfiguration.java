package telran.probes.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import telran.probes.api.UrlConstants;

@Configuration
public class SecurityConfiguration {
	@Value("${app.update.emails.role:EMAILS_ADMIN}")
	String emailsAdminRole;
	@Value("${app.update.range.role:RANGES_ADMIN}")
	String rangeAdminRole;
	@Value("${app.use.accounts.role:ACCOUNTS_USER}")
	String accountsUserRole;
	@Value("${app.use.emails.role:EMAILS_USER}")
	String emailsUserRole;
	@Value("${app.use.ranges.user:RANGES_USER}")
	String rangesUserRole;
	@Value("${app.accounts.provider.url:/accounts}")
	String accountsUrl;
	@Value("${app.sensor.email.provider.url:/emails/sensor}")
	String emailsSensorUrl;
	@Value("${app.sensor.range.provider.url:/range/sensor}")
	String rangeSensorUrl;
	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@Bean
	SecurityFilterChain configure (HttpSecurity http) throws Exception {
		http.cors(custom -> custom.disable());
		http.csrf(custom -> custom.disable());
		http.authorizeHttpRequests(requests -> requests
				.requestMatchers(UrlConstants.UPDATE_EMAILS).hasRole(emailsAdminRole)
				.requestMatchers(UrlConstants.UPDATE_RANGE).hasRole(rangeAdminRole)
				.requestMatchers(accountsUrl).hasRole(accountsUserRole)
				.requestMatchers(emailsSensorUrl).hasRole(emailsUserRole)
				.requestMatchers(rangeSensorUrl).hasRole(rangesUserRole)
				.anyRequest().authenticated());
		http.httpBasic(Customizer.withDefaults());
		return http.build();
	}

}
