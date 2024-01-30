package telran.probes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.ComponentScan;

import lombok.RequiredArgsConstructor;
import telran.probes.service.SensorsService;

@SpringBootApplication
@ComponentScan(basePackages = {"telran"})

public class SensorsAppl {


	public static void main(String[] args) {
		SpringApplication.run(SensorsAppl.class, args);
		

	}
     
}
