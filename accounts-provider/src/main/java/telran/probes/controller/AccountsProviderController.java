package telran.probes.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.AccountDto;
import telran.probes.service.AccountsProviderService;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class AccountsProviderController {
	final AccountsProviderService accountsProviderService;

	@GetMapping("${app.accounts.provider.url}" + "/{email}")
	AccountDto getAccount(@PathVariable(name = "email") @NotNull @Email String email) {
		log.debug("email: {} has been received", email);
		return accountsProviderService.getAccount(email);
	}
}
