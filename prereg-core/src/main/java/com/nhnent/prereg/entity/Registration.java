package com.nhnent.prereg.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
* The Registration of mobile number
*
* @author  Tuan Nguyen
* @version 1.0
* @since   2016-09-13
*/
@Entity
@Table(name = "registration")
public class Registration implements Serializable {

	/**
	 * Unique id of serialized object
	 */
	private static final long serialVersionUID = -5679459402481748921L;
	
	/**
	 * Surrogate id of persisted object
	 */
	private Long id;
	
	/**
	 * Registered mobile no, which used to identify registration
	 */
	private String mobileNo;
	
	/**
	 * Time of registration
	 */
	private Timestamp registeredTime;
	
	/**
	 * The referred registration of this registration
	 */
	private String referredBy;
	
	/**
	 * The number of registration that refer by this registration
	 */
	private Long numberOfRefer;
	
	/*
	 * Constructors
	 */
	
	/**
	 * No argument constructor
	 */
	public Registration() {
		this.id = null;
		this.mobileNo = null;
		this.registeredTime = null;
		this.referredBy = null;
		this.numberOfRefer = 0L;
	}

	/**
	 * Constructor using fields
	 */
	public Registration(Long id, String mobileNo, Timestamp registeredTime, String referredBy, Long numberOfRefer) {
		super();
		this.id = id;
		this.mobileNo = mobileNo;
		this.registeredTime = registeredTime;
		this.referredBy = referredBy;
		this.numberOfRefer = numberOfRefer;
	}

	/*
	 * Get/set methods
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "mobile_no", length = 255, nullable = false, unique = true, updatable = false)
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "registered_time", nullable = false, updatable = false)
	public Timestamp getRegisteredTime() {
		return registeredTime;
	}

	public void setRegisteredTime(Timestamp registeredTime) {
		this.registeredTime = registeredTime;
	}

	@JsonInclude(Include.NON_NULL)
	@Column(name = "referred_by", length = 255)
	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	@Column(name = "reference_num", nullable = false)
	public Long getNumberOfRefer() {
		return numberOfRefer;
	}

	public void setNumberOfRefer(Long numberOfRefer) {
		this.numberOfRefer = numberOfRefer;
	}

}
