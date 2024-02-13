package telran.probes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import telran.probes.dto.AccountDto;

@Document(collection = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {
	@Id
	String email;
	String hashPassword;
	String[] roles;	
	
	public Account (AccountDto accountDto) {
		this.email = accountDto.email();
		this.hashPassword = accountDto.password();
		this.roles = accountDto.roles();
		
	}
	
	public AccountDto buildDto () {
		return new AccountDto(email, hashPassword, roles);
	}
}
