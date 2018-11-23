
package com.sample.warehouse.response;

/**
 * This contains info as: process duration, number of imported deals and number of invalid
 * records
 * @author Pranish Dahal
 */
public class UploadedFileMetaInfoResponse {

	private Long processDuration;
	private Boolean isValid;
	private Long validImportedRecords;
	private Long invalidImportedRecords;

	/**
	 * @return the validImportedRecords
	 */
	public Long getValidImportedRecords() {
		return validImportedRecords;
	}

	/**
	 * @param validImportedRecords the validImportedRecords to set
	 */
	public void setValidImportedRecords(Long validImportedRecords) {
		this.validImportedRecords = validImportedRecords;
	}

	/**
	 * @return the invalidImportedRecords
	 */
	public Long getInvalidImportedRecords() {
		return invalidImportedRecords;
	}

	/**
	 * @param invalidImportedRecords the invalidImportedRecords to set
	 */
	public void setInvalidImportedRecords(Long invalidImportedRecords) {
		this.invalidImportedRecords = invalidImportedRecords;
	}

	/**
	 * @return the isValid
	 */
	public Boolean getIsValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	/**
	 * @return the processDuration
	 */
	public Long getProcessDuration() {
		return processDuration;
	}

	/**
	 * @param processDuration the processDuration to set
	 */
	public void setProcessDuration(Long processDuration) {
		this.processDuration = processDuration;
	}

}
