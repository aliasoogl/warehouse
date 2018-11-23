package com.sample.warehouse.domain;

import javax.persistence.MappedSuperclass;

import com.sample.warehouse.dto.RecordDto;

/**
 * Acts as a base from Valid and Invalid Records.
 * @author Pranish Dahal
 * @version 2.0.0
 * @since , Nov 20, 2018
 */
@MappedSuperclass
public class Record extends AbstractEntity {

	private String recordId;
	private String orderingCurrency;
	private String toCurrency;
	private String dealAmount;

	public Record() {

	}

	public Record(RecordDto record) {
		this.recordId = record.getId();
		this.orderingCurrency = record.getOrderingCurrency();
		this.toCurrency = record.getToCurrency();
		this.dealAmount = record.getDealAmount();
	}

	/**
	 * @return the recordId
	 */
	public String getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return the orderingCurrency
	 */
	public String getOrderingCurrency() {
		return orderingCurrency;
	}

	/**
	 * @param orderingCurrency the orderingCurrency to set
	 */
	public void setOrderingCurrency(String orderingCurrency) {
		this.orderingCurrency = orderingCurrency;
	}

	/**
	 * @return the toCurrency
	 */
	public String getToCurrency() {
		return toCurrency;
	}

	/**
	 * @param toCurrency the toCurrency to set
	 */
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	/**
	 * @return the dealAmount
	 */
	public String getDealAmount() {
		return dealAmount;
	}

	/**
	 * @param dealAmount the dealAmount to set
	 */
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}

}
