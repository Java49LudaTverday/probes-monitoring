package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import telran.probes.dto.ProbeData;
import telran.probes.model.ProbeDataDoc;
import telran.probes.repo.ProbeDataRepo;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgPopulatorTest {
	private static final long SENSORE_ID = 123l;
	private static final ProbeData RECEIVED_PROBE_DATA = new ProbeData(SENSORE_ID, 50f, 0);
	@Autowired
	InputDestination producer;
	@Autowired
	ProbeDataRepo probeRepo;
	String bindingNameConsumer = "consumerProbeData-in-0";
	
	@Test
	void test() {
		probeRepo.deleteAll();
		producer.send(new GenericMessage<ProbeData>(RECEIVED_PROBE_DATA), bindingNameConsumer);
		ProbeDataDoc actual = probeRepo.findById(SENSORE_ID).orElse(null);
		assertNotNull(actual);
		ProbeData actualProbeData = new ProbeData(actual.getSensorId(), actual.getValue(), 0);
		assertEquals(RECEIVED_PROBE_DATA, actualProbeData);
	}

}
