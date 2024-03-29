package telran.probes.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.probes.dto.SensorEmailsDto;
import telran.probes.dto.SensorRange;
import telran.probes.dto.SensorRangeDto;
import telran.probes.exceptions.IllegalSensorRangeException;
import telran.probes.exceptions.SensorNotFoundException;
import telran.probes.model.SensorEmailsDoc;
import telran.probes.model.SensorRangeDoc;
import telran.probes.repo.SensorEmailsRepo;
import telran.probes.repo.SensorRangeRepo;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminConsoleServiceImpl implements AdminConsoleService {

	final SensorEmailsRepo sensorEmailsRepo;
	final SensorRangeRepo sensorRangeRepo;
	final StreamBridge streamBridge;
	@Value("${app.sensors.update.binding.name:sensorsUpdate-out-0}")
	String bindingName;
	@Value("${app.update.token.emails:emails-update}")
	String emailsUpdateToken;
	@Value("${app.update.message.delimiter:#}")
	String delimiter;
	@Value("${app.update.token.range:range-update}")
	String rangeUpdateToken;
		@Override
		public SensorRangeDto updateSensorRange(SensorRangeDto sensorRangeDto) {
			checkRange(sensorRangeDto.sensorRange());
			SensorRangeDoc oldDoc = sensorRangeRepo.findById(sensorRangeDto.sensorId())
					.orElseThrow(() -> new SensorNotFoundException());
			log.debug("service: old range of the sensor {} is {}",
					sensorRangeDto.sensorId(), new SensorRange(oldDoc.getMinValue(), oldDoc.getMaxValue()));
			SensorRangeDoc sensorRangeDoc = SensorRangeDoc.of(sensorRangeDto);
			SensorRangeDto res = sensorRangeRepo.save(sensorRangeDoc).build();
			log.debug("service: new range of the sensor {} is {}",
					sensorRangeDto.sensorId(), res.sensorRange());
			sendMessage(rangeUpdateToken, sensorRangeDto.sensorId());
			return res;
		}

		private void checkRange(SensorRange sensorRange) {
			if(sensorRange.maxValue() <= sensorRange.minValue()) {
				throw new IllegalSensorRangeException();
			}
			
		}

		private void sendMessage(String updateToken, Long sensorId) {
			String message = String.format("%s%s%d", updateToken, delimiter,sensorId);
			streamBridge.send(bindingName, message);
			log.debug("message: {} has been sent to {}", message, bindingName);
			
		}

		@Override
		public SensorEmailsDto updateSensorEmails(SensorEmailsDto sensorEmails) {
			SensorEmailsDoc oldDoc = sensorEmailsRepo.findById(sensorEmails.sensorId())
					.orElseThrow(() -> new SensorNotFoundException());
			log.debug("service: old emails of the sensor {} is {}",
					sensorEmails.sensorId(), Arrays.deepToString(oldDoc.getEmails()));
			SensorEmailsDoc sensorEmailsDoc = SensorEmailsDoc.of(sensorEmails);
			SensorEmailsDto res = sensorEmailsRepo.save(sensorEmailsDoc).build();
			log.debug("service: new emails of the sensor {} is {}",
					sensorEmails.sensorId(), Arrays.deepToString(res.emails()));
			sendMessage(emailsUpdateToken, sensorEmails.sensorId());
			return res;
		}

}
