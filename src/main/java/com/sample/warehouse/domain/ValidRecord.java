
package com.sample.warehouse.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sample.warehouse.dto.RecordDto;

@Entity
public class ValidRecord extends Record {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "deal_id")
	private Deal deal;

	public ValidRecord() {

	}

	/**
	 * @param r
	 */
	public ValidRecord(RecordDto r) {
		super(r);
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

}
