package com.sample.warehouse.exception;

/**
 * POJO for storing error message of exceptions.
 * @author Pranish Dahal
 * 
 */
public class ErrorDetail {

	private String message;
	private Integer status;

	/**
	 * @param value
	 * @param message2
	 */
	public ErrorDetail(int value, String message2) {
		this.status = value;
		this.message = message2;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

}
