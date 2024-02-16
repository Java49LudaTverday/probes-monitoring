package telran.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.probes.api.ErrorMessages;
import telran.probes.dto.AccountDto;
import telran.probes.model.Account;
import telran.probes.repo.AccountRepo;


@SpringBootTest
@AutoConfigureMockMvc
class AccountProviderApplTest {
	private static final String EMAIL_1 = "email@gmail.com";
	private static final Account ACCOUNT_1 = new Account(EMAIL_1, "12345678", new String[] {"role1"});
	String EMAIL_NOT_FOUND = "emailNotFound@gmail.com";
	String EMAIL_WRONG_FORMAT = "emailWrongFormat";
	@Autowired
	MockMvc mockMvc;
	@Value("${app.accounts.provider.url}")
	String url;
	@Autowired
	AccountRepo accountRepo;
	@Autowired
	ObjectMapper mapper;
	
	@BeforeEach
	void setUp () {
		accountRepo.save(ACCOUNT_1);
	}
	

	@Test
	void getAccount_whenNormalFlow_thenReturnAccountDto() throws Exception {
		String fullUrl = "http://localhost:8080"+ url + "/" + EMAIL_1;
		String response= mockMvc.perform(get(fullUrl)).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Account account = mapper.readValue(response, Account.class);
		assertEquals(ACCOUNT_1.getEmail(), account.getEmail());
	}
	@Test
	void getAccount_whenEmailNotFound_thenReturnResponseMessage() throws Exception {
		String fullUrl = "http://localhost:8080"+ url + "/" + EMAIL_NOT_FOUND;
		String response= mockMvc.perform(get(fullUrl)).andDo(print())
				.andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		assertEquals("account: emailNotFound@gmail.com does not exist", response);
	}
	@Test
	void getAccount_whenEmailWrongFormat_thenReturnResponseMessage() throws Exception {
		String fullUrl = "http://localhost:8080"+ url + "/" + EMAIL_WRONG_FORMAT;
		String response = mockMvc.perform(get(fullUrl)).andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals(ErrorMessages.WRONG_EMAIL_FORMAT, response);
	}

}
