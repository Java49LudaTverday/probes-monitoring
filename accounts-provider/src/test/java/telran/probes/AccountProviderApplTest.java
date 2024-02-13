package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import telran.probes.service.AccountsProviderService;

@SpringBootTest
class AccountProviderApplTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	AccountsProviderService accountProviderService;
	

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
