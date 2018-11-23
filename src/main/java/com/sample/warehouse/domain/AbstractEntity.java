
package com.sample.warehouse.domain;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;



/**
 * Holds the entity fields required for most of the tables.
 * @author Pranish Dahal
 * 
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

	@Temporal(TemporalType.TIMESTAMP)
	protected Date createdDate = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	protected Date modifiedDate = new Date();

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
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
