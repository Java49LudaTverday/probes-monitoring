package telran.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import telran.probes.dto.ProbeData;
import telran.probes.dto.SensorRange;
import telran.probes.service.SensorRangeProviderService;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class AnalyzerServiceTest {

	@Autowired
	InputDestination producer;
	@MockBean
	RestTemplate restTemplate;
	@Autowired
	SensorRangeProviderService providerService;
	String bindingNameConsumer = "configChangeConsumer-in-0";
	
	static final long SENSOR_ID = 123l;
	static final long SENSOR_ID_NOT_EXIST = 125l;
	static final long SENSOR_ID_NO_RANGE = 124l;
	private static final float MIN_VALUE = 10;
	private static final float MAX_VALUE = 20;
	private static final float MIN_UPDATED_VALUE = 20;
	private static final float MAX_UPDATED_VALUE = 30;
	static final String NOT_FOUND_MESSAGE = "sensor with id 123 not exists";
	static final String delimiter = "#";
	static final String RANGE_UPDATE_TOKEN = "range-update" + delimiter + SENSOR_ID;
	static final String EMAIL_UPDATE_TOKEN = "email-update" + delimiter + SENSOR_ID;
	
	static final SensorRange SENSOR_RANGE = new SensorRange(MIN_VALUE, MAX_VALUE);
	final SensorRange SENSOR_RANGE_DEFAULT_VALUES = new SensorRange(0, 20);
	final SensorRange SENSOR_RANGE_UPDATED_VALUES = new SensorRange(MIN_UPDATED_VALUE, MAX_UPDATED_VALUE);
	

	@SuppressWarnings("unchecked")
	@Test
	@Order(1)
	void normalFlow_WithNoMapData_thenSavedIntoMap() {
		ResponseEntity<SensorRange> responseEntity = new ResponseEntity<SensorRange>(SENSOR_RANGE, HttpStatus.OK);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
				.thenReturn(responseEntity);
		SensorRange actual = providerService.getSensorRange(SENSOR_ID);
		assertEquals(SENSOR_RANGE, actual);

	}

	@Test
	@Order(2)
	void normalFlow_WithMapData_thenReturnRange() {

		SensorRange actual = providerService.getSensorRange(SENSOR_ID);
		assertEquals(SENSOR_RANGE, actual);

	}

	@SuppressWarnings("unchecked")
	@Test
	@Order(3)
	void altenativeFlow_withNoSensorInDB_thenReturnMessageAnd404() {
		// TODO
		ResponseEntity<String> responseEntity = new ResponseEntity<>(NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
				.thenReturn(responseEntity);
		SensorRange actualRange = providerService.getSensorRange(SENSOR_ID_NOT_EXIST);
		assertEquals(SENSOR_RANGE_DEFAULT_VALUES, actualRange);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Order(4)
	void altenativeFlow_withRemoteServiceNotAvailable_thenThrowsException() {
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
				.thenThrow(RestClientException.class);
		SensorRange actualRange = providerService.getSensorRange(SENSOR_ID_NOT_EXIST);
		assertEquals(SENSOR_RANGE_DEFAULT_VALUES, actualRange);
	}

	@SuppressWarnings("unchecked")
	@Test
	@Order(5)
	void normalFlow_withExistingRangeInMap_thenUpdatedRange() {
		ResponseEntity<SensorRange> responseEntity = new ResponseEntity<>(SENSOR_RANGE_UPDATED_VALUES, HttpStatus.OK);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
				.thenReturn(responseEntity);
		producer.send(new GenericMessage<String>(RANGE_UPDATE_TOKEN), bindingNameConsumer);
		SensorRange actual = providerService.getSensorRange(SENSOR_ID);
		assertEquals(actual.minValue(), MIN_UPDATED_VALUE);
		assertEquals(actual.maxValue(), MAX_UPDATED_VALUE);
	}
	
	@Test
	@Order(6)
	void normalFlow_whenUpdatedEmail_thenNothin() {
		producer.send(new GenericMessage<String>(EMAIL_UPDATE_TOKEN), bindingNameConsumer);
		SensorRange actual = providerService.getSensorRange(SENSOR_ID);
		assertEquals(actual, SENSOR_RANGE_UPDATED_VALUES);
	}
	@SuppressWarnings("unchecked")
	@Test
	@Order(7)
	void normalFlow_withNoRangeInMap_thenUpdatedRange() {
		ResponseEntity<SensorRange> responseEntityRangeNull = new ResponseEntity<>(null, HttpStatus.OK);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
				.thenReturn(responseEntityRangeNull);
		SensorRange actualRange = providerService.getSensorRange(SENSOR_ID_NO_RANGE);
		assertNull(actualRange);
		ResponseEntity<SensorRange> responseEntity = new ResponseEntity<>(SENSOR_RANGE_UPDATED_VALUES, HttpStatus.OK);
		when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), any(Class.class)))
				.thenReturn(responseEntity);
		producer.send(new GenericMessage<String>(RANGE_UPDATE_TOKEN), bindingNameConsumer);
		SensorRange actual = providerService.getSensorRange(SENSOR_ID_NO_RANGE);
		assertEquals(actual.minValue(), MIN_UPDATED_VALUE);
		assertEquals(actual.maxValue(), MAX_UPDATED_VALUE);
		
	}

	

}
