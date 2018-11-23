package com.sample.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles the exceptions.
 * @author Pranish Dahal
 * 
 */
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorDetail handleMethodArgumentNotValidException(StorageException exception) {
		return new ErrorDetail(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}

}
