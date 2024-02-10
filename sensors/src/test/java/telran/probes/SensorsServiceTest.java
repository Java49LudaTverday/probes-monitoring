package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import telran.probes.configuration.SensorsConfiguration;
import telran.probes.dto.ProbeData;

@SpringBootTest
@Slf4j
@Import(TestChannelBinderConfiguration.class)
class SensorsServiceTest {
	@Autowired
	OutputDestination consumer;
	@Autowired
	SensorsConfiguration sensorsConfiguration;
	@Test
	void test() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String bindingName = sensorsConfiguration.getBindingName();
		long timestamp = System.currentTimeMillis();
		while (System.currentTimeMillis() - timestamp < SensorsAppl.TIMEOUT) {
			Message<byte[]> message = consumer.receive(1000, bindingName);
			if (message != null) {
				ProbeData probeData = mapper.readValue(message.getPayload(), ProbeData.class);
				log.debug("test: {}", probeData);
			}
			Thread.sleep(1000);

		}
	}

}
