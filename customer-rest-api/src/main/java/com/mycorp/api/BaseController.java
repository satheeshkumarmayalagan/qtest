package com.mycorp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mycorp.api.exception.ErrorMessage;
import com.mycorp.api.exception.ResourceNotFoundException;
import com.mycorp.api.exception.ValidationError;
import com.mycorp.api.exception.ValidationErrorBuilder;

public class BaseController {

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ValidationError handleException(MethodArgumentNotValidException exception) {
		log.error("Handle Bad Request exception - " + exception.getMessage());
		exception.printStackTrace();
		return createValidationError(exception);
	}

	private ValidationError createValidationError(MethodArgumentNotValidException exception) {
		return ValidationErrorBuilder.fromBindingErrors(exception.getBindingResult());
	}
	
	@ExceptionHandler(value = { ResourceNotFoundException.class})
	public ErrorMessage handleException(ResourceNotFoundException  exception) {
		log.error("Handle ResourceNotFoundException - " + exception.getMessage());
		exception.printStackTrace();
		return new ErrorMessage("The requested resource could not be found");
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ErrorMessage handleException(AccessDeniedException  exception) {
		log.error("Handle AccessDeniedException - " + exception.getMessage());
		exception.printStackTrace();
		return new ErrorMessage("Access denied.");
	}

	@ExceptionHandler
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleException(Exception  exception) {
		log.error("Handle all other exception - " + exception.getMessage());
		exception.printStackTrace();
		return new ErrorMessage("There was an error processesing the request. Please try again or contact support");
	}

}
