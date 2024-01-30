package telran.probes.test;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import telran.probes.dto.SensorRange;
import telran.probes.service.SensorRangeProviderService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static telran.probes.api.UrlConstants.*;


@SpringBootTest
@AutoConfigureMockMvc
public class SensorRangeProviderTest {
	@Autowired
	MockMvc mockMvc;
	@Autowired
	SensorRangeProviderService sensorService;
	@Autowired
	ObjectMapper mapper;
	@Autowired
	DbTestCreation dbCreation;

	private static final String HOST = "http://localhost:8090";
	@Value("${app.sensor.range.provider.url}")
	String url;
	
	@BeforeEach
	void setUp() {
		dbCreation.createDB();
	}

	@Test
	@DisplayName("Cheaks loading context")
	void loadingContextTest() {
		assertNotNull(mockMvc);
		assertNotNull(sensorService);
		assertNotNull(mapper);
		assertNotNull(dbCreation);
	}

	@Test
	@DisplayName("Getting range with normal id")
	void getSensorRange_NormalId_RangeOfSensore() throws Exception {
		SensorRange range = sensorService.getRangeSensor(dbCreation.ID_1);
		assertEquals(dbCreation.RANGE_1, range);
		String expected = mockMvc.perform(get(HOST + url+ "/{id}", dbCreation.ID_1))
				.andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString();
		String actual = mapper.writeValueAsString(range);
		assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Trying get range with wrong id")
	void getSensorRange_WrongId_NotFoundException() throws Exception {
		String response = mockMvc.perform(get(HOST + url+ "/{id}", 999))
				.andExpect(status().isNotFound()).andReturn()
				.getResponse().getContentAsString();
		assertEquals("sensor 999 not exist", response);
	}

	@Test
	@DisplayName("Trying receive wrong format id")
	void getSensoreRangeWrongFormatId() throws Exception {
		Long wrongId = -1l;
		String response = mockMvc.perform(get(HOST + url+ "/{id}", wrongId))
				.andExpect(status().isBadRequest()).andReturn()
				.getResponse().getContentAsString();
		assertEquals("must be greater than 0", response);
	}

}
