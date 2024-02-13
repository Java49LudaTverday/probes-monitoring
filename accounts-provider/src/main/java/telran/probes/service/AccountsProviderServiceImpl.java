package telran.probes.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.exceptions.NotFoundException;
import telran.probes.dto.AccountDto;
import telran.probes.model.Account;
import telran.probes.repo.AccountRepo;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountsProviderServiceImpl implements AccountsProviderService {
final AccountRepo accountRepo;

	@Override
	public AccountDto getAccount(String email) {
		Account account = accountRepo.findById(email).orElseThrow(()->
		new NotFoundException(String.format("account: %s does not exist", email)));
		log.debug("account: {} has been found", account);
		return account.buildDto();
	}

}
