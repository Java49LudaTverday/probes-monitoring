package telran.probes;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.ProbeData;
import telran.probes.service.AvgValueService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class AvgReducerAppl {
	final AvgValueService avgValueService;
	final StreamBridge streamBreage;
	@Value("${app.value.binding.name}")
	String avgValueBindingName;
	
	public static void main(String[] args) {
		SpringApplication.run(AvgReducerAppl.class, args);	

	}

	@Bean
	Consumer<ProbeData> consumerProbeDataAvg() {
		return this::consumeMethod;
	}
	void consumeMethod (ProbeData probeData) {
		log.trace("received probe: {}", probeData);
		Long avgValue = avgValueService.getAvgValue(probeData);
		log.trace("get avarage value: {}",avgValue );
		if(avgValue != null && avgValue != 0) {
			log.debug("send avarage value {}", avgValue);
			streamBreage.send(avgValueBindingName, 
					new ProbeData(probeData.sensorId(), avgValue, System.currentTimeMillis()));
		}
	}
}
