package telran.probes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SensorsServiceTest {
	@Autowired
	SensorsDbCreation dbCreation;

	@BeforeEach
	void setUp () {
		dbCreation.createDb();
	}
	@Test
	void test() throws Exception {
		Thread.sleep(10000);
	}

}
