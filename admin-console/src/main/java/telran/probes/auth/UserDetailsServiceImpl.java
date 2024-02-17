package telran.probes.auth;


import java.util.Arrays;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.AccountDto;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{

	final RestTemplate restTemplate;
	final AccountsProviderConfiguration accountsProviderConfiguration;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountDto account = null;
		try {
			account = getAccountFromService(username);
		} catch (Exception e) {
			new UsernameNotFoundException("");
		}
		String[] roles = Arrays.stream(account.roles()).map(r -> "ROLE_" + r).toArray(String[]::new);
		return new User(account.email(), account.password(), AuthorityUtils.createAuthorityList(roles));
	}

	private AccountDto getAccountFromService(String username) {
		ResponseEntity<?> responseEntity = 
				restTemplate.exchange(getFullUrl(username), HttpMethod.GET, null, AccountDto.class);
//		restTemplate.getForObject(getFullUrl(username), AccountDto.class);
		if(!responseEntity.getStatusCode().is2xxSuccessful()) {
			new Exception((String)responseEntity.getBody());
		}
		AccountDto res = (AccountDto) responseEntity.getBody();
		return res;
	}

	private String getFullUrl(String username) {
		String res = String.format("http://%s:%d%s/%s",
				accountsProviderConfiguration.getHost(), 
				accountsProviderConfiguration.getPort(),
				accountsProviderConfiguration.getUrl(),
				username);
		log.debug("url: {}", res);
		return res;
	}

}
