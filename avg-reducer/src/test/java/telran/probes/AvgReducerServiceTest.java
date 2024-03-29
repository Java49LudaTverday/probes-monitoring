package telran.probes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import telran.probes.dto.ProbeData;
import telran.probes.model.ProbesList;
import telran.probes.repo.ProbesListRepo;
import telran.probes.service.AvgValueService;

import java.util.*;

@SpringBootTest

class AvgReducerServiceTest {
	static final long SENSOR_ID_NO_REDIS_RECORD = 123l;
	static final long SENSOR_ID_NO_AVG = 124l;
	static final long SENSOR_ID_AVG = 125L;
	static final float VALUE = 100f;
	static final ProbeData PROBE_NO_REDIS_RECORD = new ProbeData(SENSOR_ID_NO_REDIS_RECORD, VALUE, 0);
	static final ProbeData PROBE_NO_AVG = new ProbeData(SENSOR_ID_NO_AVG, VALUE, 0);
	static final ProbeData PROBE_AVG = new ProbeData(SENSOR_ID_AVG, VALUE, 0);
	List<Float> VALUES_NO_AVG;
	List<Float> VALUES_AVG;

	final ProbesList PROBES_LIST_NO_AVG = new ProbesList(SENSOR_ID_NO_AVG);
	final ProbesList PROBES_LIST_AVG = new ProbesList(SENSOR_ID_AVG);
	final ProbesList PROBES_LIST_NO_RECORD = new ProbesList(SENSOR_ID_NO_REDIS_RECORD);
	final Map<Long, ProbesList> mapRedis = new HashMap<>();

	@Autowired
	AvgValueService avgValueService;
	@MockBean
	ProbesListRepo probesListRepo;

	@BeforeEach
	void setUp() {
		VALUES_NO_AVG = PROBES_LIST_NO_AVG.getValues();
		VALUES_AVG = PROBES_LIST_AVG.getValues();
		VALUES_AVG.add(VALUE);
		mapRedis.put(SENSOR_ID_NO_AVG, PROBES_LIST_NO_AVG);
		mapRedis.put(SENSOR_ID_AVG, PROBES_LIST_AVG);
	}

	@Test
	void testNoRedisRecord_savedIntoRedis() {
		when(probesListRepo.findById(SENSOR_ID_NO_REDIS_RECORD)).thenReturn(Optional.ofNullable(null));
		when(probesListRepo.save(PROBES_LIST_NO_RECORD)).thenAnswer(new Answer<ProbesList>() {

			@Override
			public ProbesList answer(InvocationOnMock invocation) throws Throwable {
				mapRedis.put(SENSOR_ID_NO_REDIS_RECORD, invocation.getArgument(0));
				return invocation.getArgument(0);
			}
		});
		Long res = avgValueService.getAvgValue(PROBE_NO_REDIS_RECORD);
		assertNull(res);
		ProbesList probesListRedis = mapRedis.get(SENSOR_ID_NO_REDIS_RECORD);
		assertNotNull(probesListRedis);
		assertEquals(VALUE, probesListRedis.getValues().get(0));
	}

	@Test
	void testNoAvgValue_returnNull() {
		assertTrue(mapRedis.get(SENSOR_ID_NO_AVG).getValues().isEmpty());
		when(probesListRepo.findById(SENSOR_ID_NO_AVG)).thenReturn(Optional.of(PROBES_LIST_NO_AVG));
		when(probesListRepo.save(PROBES_LIST_NO_AVG)).thenAnswer(new Answer<ProbesList>() {

			@Override
			public ProbesList answer(InvocationOnMock invocation) throws Throwable {
				ProbesList probesList = invocation.getArgument(0);
				mapRedis.put(SENSOR_ID_NO_AVG, probesList);
				return probesList;
			}
		});
		Long res = avgValueService.getAvgValue(PROBE_NO_AVG);
		assertNull(res);
		List<Float> newList = mapRedis.get(SENSOR_ID_NO_AVG).getValues();
		assertEquals(1, newList.size());
		assertIterableEquals(VALUES_NO_AVG, newList);
		assertEquals(VALUE, newList.get(0));
	}

	@Test
	void testAvgValue_returnValue() {
		assertEquals(mapRedis.get(SENSOR_ID_AVG).getValues().size(), 1);
		when(probesListRepo.findById(SENSOR_ID_AVG)).thenReturn(Optional.of(PROBES_LIST_AVG));
		when(probesListRepo.save(PROBES_LIST_AVG)).thenAnswer(new Answer<ProbesList>() {

			@Override
			public ProbesList answer(InvocationOnMock invocation) throws Throwable {
				mapRedis.put(SENSOR_ID_AVG, invocation.getArgument(0));
				return invocation.getArgument(0);
			}
		});
		Long res = avgValueService.getAvgValue(PROBE_AVG);
		assertEquals(100, res);
		List<Float> list = mapRedis.get(SENSOR_ID_AVG).getValues();
		assertTrue(list.isEmpty());
	}
}
