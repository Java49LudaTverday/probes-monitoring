package telran.probes.service;

import java.util.List;

import telran.probes.dto.EmailData;


public interface EmailDataProviderService {
	String[] getEmails(long sensorId);
}
