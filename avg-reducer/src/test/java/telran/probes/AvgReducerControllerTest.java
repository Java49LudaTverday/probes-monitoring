package telran.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import telran.probes.dto.ProbeData;
import telran.probes.service.AvgValueService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
class AvgReducerControllerTest {
	private static final ProbeData RECEIVED_PROBE_DATA = new ProbeData(123L, 50f, 0);
	private static final long AVG_VALUE = 60l;
	private static final ProbeData AVG_VALUE_PROBE_DATA = new ProbeData(123L,(float) AVG_VALUE, 0);
	@Autowired
	InputDestination producer;
	@Autowired
	OutputDestination consumer;
	String bindingNameProducer = "value-out-0";
	String bindingNameConsumer = "consumerProbeDataAvg-in-0";
	@MockBean
	AvgValueService avgValueService;
    ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void getAvgValue_returneNull_nothingProduce() {
		when(avgValueService.getAvgValue(RECEIVED_PROBE_DATA)).thenReturn(null);
		producer.send(new GenericMessage<ProbeData>(AVG_VALUE_PROBE_DATA), bindingNameConsumer);
		Message<byte[]> message = consumer.receive(10, bindingNameProducer);
		assertNull(message);
	}
	
	@Test
	void getAvgValue_returneValue_produceProbeData() throws Exception {
		when(avgValueService.getAvgValue(RECEIVED_PROBE_DATA))
		.thenReturn(AVG_VALUE);
		producer.send(new GenericMessage<ProbeData>(RECEIVED_PROBE_DATA), bindingNameConsumer);
		Message<byte[]> message = consumer.receive(10, bindingNameProducer);
		assertNotNull(message);
		ProbeData actual = mapper.readValue(message.getPayload(), ProbeData.class);
		assertEquals(AVG_VALUE_PROBE_DATA, actual);
	}

}
