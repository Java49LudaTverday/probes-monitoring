package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AnalyzerServiceTest {
	@Autowired
	InputDestination consumer;
	@MockBean
	RestTemplate restTemplate;

	@Test
	void loadingContext_expectedNotNull() {
		assertNotNull(consumer);
		assertNotNull(restTemplate);
	}

}
