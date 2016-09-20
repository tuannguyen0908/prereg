package com.nhnent.prereg.service;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.nhnent.prereg.entity.Registration;
import com.nhnent.prereg.exception.IllegalReferMobileException;
import com.nhnent.prereg.exception.MobileAlreadyExistedException;
import com.nhnent.prereg.repository.RegistrationRepository;
import com.nhnent.prereg.repository.support.PageableUtils;
import com.nhnent.prereg.repository.support.RegistrationSpecifications;
import com.nhnent.prereg.support.GridRequest;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {
	private RegistrationRepository registrationRepository;
	
	@Autowired
	public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
		this.registrationRepository = registrationRepository;
	}

	/*
	 * Register new mobile number as a Registration. 
	 * This method also try to maintain the number of references of Registration(s) of new mobile number and the referred mobile number
	 * 
	 * @param mobileNo the new mobile number need to register
	 * @param referredMobileNo the mobile number which referred by new mobile number
	 * @return The new Registration of mobileNo
	 * @see Registration
	 */
	@Override
	public Registration register(String mobileNo, String referredMobileNo) {
		Registration registration = null;
		// preparing data
		mobileNo = mobileNo.trim();
		referredMobileNo = referredMobileNo != null ? referredMobileNo.trim() : null;
		
		if(validate(mobileNo, referredMobileNo)) {
			// check to see if referred mobile number have existed
			// if exist, update to increase the number of references by one
			if (referredMobileNo != null) {
				Registration referredBy = registrationRepository.findByMobileNo(referredMobileNo);
				if (referredBy != null) {
					Long numberOfRefer = referredBy.getNumberOfRefer();
					referredBy.setNumberOfRefer(numberOfRefer != null ? ++numberOfRefer : 0L);
				}
			}
			
			Long numberOfRefer = registrationRepository.countByReferredBy(mobileNo);
			registration = new Registration();
			registration.setId(null);
			registration.setMobileNo(mobileNo);
			registration.setRegisteredTime(new Timestamp(System.currentTimeMillis()));
			registration.setReferredBy(referredMobileNo);
			registration.setNumberOfRefer(numberOfRefer);
			registration =  registrationRepository.save(registration);
		}
		
		return registration;
	}
	
	@Override
	public Page<Registration> findAll(GridRequest data) {
		Specification<Registration> specification = RegistrationSpecifications.get(data.getFilterEntries());
		Pageable pageable = PageableUtils.get(data.getStart(), data.getLength(), data.getSorts());
		Page<Registration> page = registrationRepository.findAll(specification, pageable);
		return page;
	}
	
	@Override
	public Registration findOne(String mobileNo) {
		return registrationRepository.findOne(mobileNo);
	}
	
	private boolean validate(String mobileNo, String referredMobileNo) {
		Registration registration = registrationRepository.findByMobileNo(mobileNo);
		if (registration != null) {
			throw new MobileAlreadyExistedException();
		}
		if (mobileNo.equals(referredMobileNo)) {
			throw new IllegalReferMobileException();
		}
		return true;
	}

}
