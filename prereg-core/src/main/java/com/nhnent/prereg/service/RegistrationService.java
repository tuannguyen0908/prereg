package com.nhnent.prereg.service;

import org.springframework.data.domain.Page;

import com.nhnent.prereg.entity.Registration;
import com.nhnent.prereg.support.GridRequest;

public interface RegistrationService {
	/**
	 * Register new mobile number as a Registration. 
	 * This method also try to maintain the number of references of Registration(s) of new mobile number and the referred mobile number
	 * 
	 * @param mobileNo the new mobile number need to register
	 * @param referredMobileNo the mobile number which referred by new mobile number
	 * @return The new Registration of mobileNo
	 * @see Registration
	 */
	public Registration register(String mobileNo, String referredMobileNo);
	
	public Registration findOne(String mobileNo);
	
	public Page<Registration> findAll(GridRequest data);

}
