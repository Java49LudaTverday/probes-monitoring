package telran.probes.service;

import telran.probes.dto.AccountDto;

public interface AccountsProviderService {
AccountDto getAccount (String email);
}
