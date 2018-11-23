package com.sample.warehouse.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Table to maintain accumulative count of deals per Ordering Currency
 * @author Pranish Dahal
 * 
 */
@Entity
public class CurrencySummary extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String currencyISOCode;
	private Long dealCounts;
	private Long validDealCounts;
	private Long invalidDealCounts;

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
	 * @return the currencyISOCode
	 */
	public String getCurrencyISOCode() {
		return currencyISOCode;
	}

	/**
	 * @param currencyISOCode the currencyISOCode to set
	 */
	public void setCurrencyISOCode(String currencyISOCode) {
		this.currencyISOCode = currencyISOCode;
	}

	/**
	 * @return the dealCounts
	 */
	public Long getDealCounts() {
		return dealCounts;
	}

	/**
	 * @param dealCounts the dealCounts to set
	 */
	public void setDealCounts(Long dealCounts) {
		this.dealCounts = dealCounts;
	}

	/**
	 * @return the validDealCounts
	 */
	public Long getValidDealCounts() {
		return validDealCounts;
	}

	/**
	 * @param validDealCounts the validDealCounts to set
	 */
	public void setValidDealCounts(Long validDealCounts) {
		this.validDealCounts = validDealCounts;
	}

	/**
	 * @return the invalidDealCounts
	 */
	public Long getInvalidDealCounts() {
		return invalidDealCounts;
	}

	/**
	 * @param invalidDealCounts the invalidDealCounts to set
	 */
	public void setInvalidDealCounts(Long invalidDealCounts) {
		this.invalidDealCounts = invalidDealCounts;
	}

}
