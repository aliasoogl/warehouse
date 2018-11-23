package com.sample.warehouse.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.warehouse.domain.Deal;
import com.sample.warehouse.domain.Record;

/**
 * Contains the record files as: Deal Unique Id, From Currency ISO Code "Ordering
 * Currency", To Currency ISO Code, Deal timestamp, Deal Amount in ordering currency
 * @author Pranish Dahal
 */
public class RecordDto {

	private String id;
	private String orderingCurrency;
	private String toCurrency;
	private Date createdDate;
	private String dealAmount;

	@JsonIgnore
	private boolean isValid;

	@JsonIgnore
	private Deal deal;

	@JsonIgnore
	private boolean ignorable;

	public RecordDto() {

	}

	public RecordDto(String id, String orderingCurrency, String toCurrency,
			Date createdDate, String dealAmount) {
		super();
		this.id = id;
		this.orderingCurrency = orderingCurrency;
		this.toCurrency = toCurrency;
		this.createdDate = createdDate;
		this.dealAmount = dealAmount;
		this.isValid = Boolean.TRUE;
		this.ignorable = Boolean.FALSE;
	}

	/**
	 * @return the ignorable
	 */
	public boolean isIgnorable() {
		return ignorable;
	}

	/**
	 * @param ignorable the ignorable to set
	 */
	public void setIgnorable(boolean ignorable) {
		this.ignorable = ignorable;
	}

	/**
	 * @return the deal
	 */
	public Deal getDeal() {
		return deal;
	}

	/**
	 * @param deal the deal to set
	 */
	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String stringDate = "";
		if (createdDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stringDate = sdf.format(createdDate);
		}
		return id + "," + orderingCurrency + "," + toCurrency + "," + stringDate + ","
				+ dealAmount;
	}

	public Record getRecord() {
		Record record = new Record();
		record.setDealAmount(dealAmount);
		record.setRecordId(id);
		record.setCreatedDate(createdDate);
		record.setOrderingCurrency(orderingCurrency);
		record.setToCurrency(toCurrency);
		return record;
	}

	/**
	 * This converts the record to string based on how it should be uploaded to DB.It
	 * takes care about the order on how it is uploaded to db.
	 * @return
	 * @author Pranish Dahal
	 * @since , Modified In: @version, By @author
	 */
	public String generateForUpload() {
		String stringDate = "";
		if (createdDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			stringDate = sdf.format(createdDate);
		}
		return "," + stringDate + ",," + dealAmount + "," + orderingCurrency + "," + id
				+ "," + toCurrency + "," + deal.getId();
	}

}
