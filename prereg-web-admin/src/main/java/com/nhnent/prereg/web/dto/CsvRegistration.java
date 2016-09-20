package com.nhnent.prereg.web.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nhnent.prereg.entity.Registration;

@JsonPropertyOrder(value = {"mobileNo", "referredBy", "numberOfRefer", "registeredTime"})
public class CsvRegistration {
	
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

	public CsvRegistration() {
		
	}

	public CsvRegistration(String mobileNo, Timestamp registeredTime, String referredBy, Long numberOfRefer) {
		this.mobileNo = mobileNo;
		this.registeredTime = registeredTime;
		this.referredBy = referredBy;
		this.numberOfRefer = numberOfRefer;
	}

	@JsonProperty(value = "Mobile Num")
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@JsonProperty(value = "Time")
	public Timestamp getRegisteredTime() {
		return registeredTime;
	}

	public void setRegisteredTime(Timestamp registeredTime) {
		this.registeredTime = registeredTime;
	}

	@JsonProperty(value = "Refer. By")
	public String getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}

	@JsonProperty(value = "Number of references")
	public Long getNumberOfRefer() {
		return numberOfRefer;
	}

	public void setNumberOfRefer(Long numberOfRefer) {
		this.numberOfRefer = numberOfRefer;
	}
	
	public static CsvRegistration valueOf(Registration o) {
		CsvRegistration obj = new CsvRegistration("\"" + o.getMobileNo() + "\"", 
				o.getRegisteredTime(), 
				o.getReferredBy() != null ? "\"" + o.getReferredBy() + "\"" : "", 
				o.getNumberOfRefer());
		return obj;
	}

}
