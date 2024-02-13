package telran.probes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.AccountDto;
import telran.probes.service.AccountsProviderService;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountsProviderController {
	final AccountsProviderService accountsProviderService;

	@GetMapping("{email}")
	AccountDto getAccount(@PathVariable(name = "email") String email) {
		log.debug("email: {} has been received", email);
		return accountsProviderService.getAccount(email);
	}
}
