package telran.email_provider.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.email_provider.service.EmailDataProviderService;
import telran.probes.dto.EmailData;

@RestController
@RequiredArgsConstructor
public class EmailDataProviderController {
final EmailDataProviderService emailProviderService;

@GetMapping
List<EmailData> getEmailsPersons (long sensorId){
	return emailProviderService.getEmailsResponsiblePersons(sensorId);
}
}
