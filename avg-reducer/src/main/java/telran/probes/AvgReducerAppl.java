package telran.probes;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import telran.probes.dto.ProbeData;

@SpringBootApplication
public class AvgReducerAppl {

	public static void main(String[] args) {
		SpringApplication.run(AvgReducerAppl.class, args);	

	}

	@Bean
	public Consumer<ProbeData> consumerProbeData() {
		return this::consumeMethod;
	}
	void consumeMethod (ProbeData probeData) {
		
	}
}
