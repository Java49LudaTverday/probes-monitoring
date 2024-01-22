package telran.sensor.range_provider.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.sensor.range_provider.dto.Range;
import telran.sensor.range_provider.service.SensorRangeProviderService;
import static telran.sensor.range_provider.api.ConstraintConstants.*;

import telran.sensor.range_provider.api.*;

@RestController
@RequestMapping(UrlConstants.SENSOR)
@Validated
@RequiredArgsConstructor
@Slf4j
public class SensorRangeProviderController {
	final SensorRangeProviderService service;

	@GetMapping(UrlConstants.GET_RANGE+ "{" + UrlConstants.SENSOR_ID_IN_PATH + "}")
	Range getRangeOfSensor(@PathVariable(name = UrlConstants.SENSOR_ID_IN_PATH)
	 @NotNull @Positive Long id) {
		log.debug("received id {}", id);
		return service.getRangeSensor(id);
	}
}
