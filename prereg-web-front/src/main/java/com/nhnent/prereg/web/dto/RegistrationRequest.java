package com.nhnent.prereg.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class RegistrationRequest implements Serializable {

	/**
	 * Unique id of serialized object
	 */
	private static final long serialVersionUID = 2128474554584933840L;
	
	/*
	 * Registered mobile no, which used to identify registration
	 */
	@NotNull
	@Pattern(regexp = "^[0-9]{10}?$")
	private String mobileNo;
	
	/*
	 * The referred registration of this registration
	 */
	@Pattern(regexp = "^[0-9]{10}?$")
	private String referredMobileNo;

	/*
	 * Constructors
	 */
	public RegistrationRequest() {
		super();
	}

	public RegistrationRequest(String mobileNo, String secretCode, String referredMobileNo) {
		super();
		this.mobileNo = mobileNo;
		this.referredMobileNo = referredMobileNo;
	}

	/*
	 * Get/set methods
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getReferredMobileNo() {
		return referredMobileNo;
	}

	public void setReferredMobileNo(String referredMobileNo) {
		this.referredMobileNo = referredMobileNo;
	}

}
