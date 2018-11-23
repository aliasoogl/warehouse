package com.sample.warehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * <<Description Here>>
 * @author Pranish Dahal
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StorageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7587139771691668694L;

	/**
	 * @param string
	 */
	public StorageException(String message) {
		super(message);
	}

	/**
	 * @param string
	 * @param e
	 */
	public StorageException(String message, Throwable throwable) {
		super(message);
		throwable.printStackTrace();
	}

}
