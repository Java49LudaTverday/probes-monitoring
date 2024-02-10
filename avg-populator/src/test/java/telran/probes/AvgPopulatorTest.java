package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
	private static final long SENSOR_ID = 123l;
	private static final float VALUE = 50f;
	private static final ProbeData RECEIVED_PROBE_DATA = new ProbeData(SENSOR_ID, VALUE, 0);
	@Autowired
	InputDestination producer;
	@Autowired
	ProbeDataRepo probeRepo;
	String bindingNameConsumer = "consumerProbeDataPopulator-in-0";
	
	@Test
	void test() {
		probeRepo.deleteAll();
		producer.send(new GenericMessage<ProbeData>(RECEIVED_PROBE_DATA), bindingNameConsumer);
		List<ProbeDataDoc> docs = probeRepo.findAll();
		assertNotNull(docs);
		assertEquals(1, docs.size());
		ProbeDataDoc actualDoc = docs.get(0);
		assertEquals(VALUE, actualDoc.getValue());
		assertEquals(SENSOR_ID, actualDoc.getSensorId());
	}

}
