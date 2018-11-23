
package com.sample.warehouse.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Holds the Fx Deals
 * @author Pranish Dahal
 */
@Entity
public class Deal extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fileName;

	private Long processDuration;

	private Long validRecordSize;
	private Long invalidRecordSize;

	@OneToMany(mappedBy = "deal")
	private List<ValidRecord> validRecords;

	@OneToMany(mappedBy = "deal")
	private List<InvalidRecord> invalidRecords;

	/**
	 * @return the validRecordSize
	 */
	public Long getValidRecordSize() {
		return validRecordSize;
	}

	/**
	 * @param validRecordSize the validRecordSize to set
	 */
	public void setValidRecordSize(Long validRecordSize) {
		this.validRecordSize = validRecordSize;
	}

	/**
	 * @return the invalidRecordSize
	 */
	public Long getInvalidRecordSize() {
		return invalidRecordSize;
	}

	/**
	 * @param invalidRecordSize the invalidRecordSize to set
	 */
	public void setInvalidRecordSize(Long invalidRecordSize) {
		this.invalidRecordSize = invalidRecordSize;
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

	/**
	 * @return the validRecords
	 */
	public List<ValidRecord> getValidRecords() {
		return validRecords;
	}

	/**
	 * @param validRecords the validRecords to set
	 */
	public void setValidRecords(List<ValidRecord> validRecords) {
		this.validRecords = validRecords;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the invalidRecords
	 */
	public List<InvalidRecord> getInvalidRecords() {
		return invalidRecords;
	}

	/**
	 * @param invalidRecords the invalidRecords to set
	 */
	public void setInvalidRecords(List<InvalidRecord> invalidRecords) {
		this.invalidRecords = invalidRecords;
	}

}
