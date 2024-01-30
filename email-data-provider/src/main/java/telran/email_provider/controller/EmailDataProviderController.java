package telran.email_provider.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.email_provider.service.EmailDataProviderService;
import telran.probes.dto.EmailData;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailDataProviderController {
final EmailDataProviderService emailProviderService;

@GetMapping("/{id}")
 List<EmailData> getEmailsPersons (long sensorId){
	log.debug("received sensor id {}", sensorId);
	return emailProviderService.getEmailsResponsiblePersons(sensorId);
}
}
