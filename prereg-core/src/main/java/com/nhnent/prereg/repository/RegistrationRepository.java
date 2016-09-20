package com.nhnent.prereg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nhnent.prereg.entity.Registration;

public interface RegistrationRepository extends PagingAndSortingRepository<Registration, String>, JpaSpecificationExecutor<Registration> {
	public Registration findOne(String mobileNo);
	
	public Registration findByMobileNo(String mobileNo);
	
	public Registration findByReferredBy(String referredBy);
	public Long countByReferredBy(String referredBy);
	
	@SuppressWarnings("unchecked")
	public Registration save(Registration registration);
	
	public Page<Registration> findAll(Specification<Registration> spec, Pageable pageable);

}
