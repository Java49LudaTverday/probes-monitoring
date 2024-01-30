package telran.probes.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.api.*;
import telran.probes.dto.SensorRange;
import telran.probes.service.SensorRangeProviderService;

@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class SensorRangeProviderController {
	final SensorRangeProviderService service;

	@GetMapping("${app.sensor.range.provider.url}"+ "/{" + UrlConstants.SENSOR_ID_IN_PATH + "}")
	SensorRange getRangeOfSensor(@PathVariable(name = UrlConstants.SENSOR_ID_IN_PATH)
	 @NotNull @Positive Long id) {
		
		SensorRange sensorRange = service.getRangeSensor(id);
		log.debug("received range {} for sensor id {}",sensorRange, id);
		return sensorRange;
	}
}
