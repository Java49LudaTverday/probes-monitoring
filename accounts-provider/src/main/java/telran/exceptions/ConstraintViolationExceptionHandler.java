package telran.exceptions;


import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ConstraintViolationExceptionHandler {
	public static final String ERROR_MESSAGES_DELIMITER = ";";
	
	private ResponseEntity<String> errorResponse(String body, HttpStatus status) {
		log.error(body);
		return new ResponseEntity<>(body, status);
	}
	
	@ExceptionHandler({ConstraintViolationException.class})
	ResponseEntity<String> handlerConstraintViolation (ConstraintViolationException e){
		Set<ConstraintViolation<?>> messages = e.getConstraintViolations();
		String body = messages.stream().map(err -> err.getMessage() )
				.collect(Collectors.joining(ERROR_MESSAGES_DELIMITER));
		return errorResponse(body, HttpStatus.BAD_REQUEST);
	}

}
