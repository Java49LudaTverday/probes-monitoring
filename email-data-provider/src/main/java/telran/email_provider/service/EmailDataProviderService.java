package telran.email_provider.service;

import java.util.List;

import telran.probes.dto.EmailData;


public interface EmailDataProviderService {
List<EmailData> getEmailsResponsiblePersons (long sensorId);
}
