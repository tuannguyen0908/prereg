package com.nhnent.prereg.repository.support;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.nhnent.prereg.entity.Registration;
import com.nhnent.prereg.entity.Registration_;
import com.nhnent.prereg.support.CriteriaEntry;
import com.nhnent.prereg.support.CriteriaOperator;

public class RegistrationSpecifications {
	public static Specification<Registration> get(List<CriteriaEntry> criterias) {
		return new Specification<Registration>() {
			@Override
			public Predicate toPredicate(Root<Registration> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (CriteriaEntry criteria : criterias) {
					String column = criteria.getColumn();
					CriteriaOperator operator = criteria.getOperator();
					Object value = criteria.getValue();
					if (Registration_.mobileNo.getName().equals(column)) {
						if (CriteriaOperator.EQUAL.equals(operator)) {
							predicates.add(cb.equal(root.get(Registration_.mobileNo), value));
						} else if (CriteriaOperator.LIKE.eq(operator)) {
							predicates.add(cb.like(root.get(Registration_.mobileNo), "%" + value + "%"));
						} else if (operator == null) {
							predicates.add(cb.like(root.get(Registration_.mobileNo), "%" + value + "%"));
						}
					} else if (Registration_.referredBy.getName().equals(column)) {
						if (CriteriaOperator.EQUAL.equals(operator)) {
							predicates.add(cb.equal(root.get(Registration_.referredBy), value));
						} else if (CriteriaOperator.LIKE.eq(operator)) {
							predicates.add(cb.like(root.get(Registration_.referredBy), "%" + value + "%"));
						} else if (operator == null) {
							predicates.add(cb.like(root.get(Registration_.referredBy), "%" + value + "%"));
						}
					} else if (Registration_.registeredTime.getName().equals(column)) {
						if (value instanceof Number) {
							if (CriteriaOperator.GREATER_OR_EQUAL.eq(operator)) {
								predicates.add(cb.greaterThanOrEqualTo(root.get(Registration_.registeredTime), new Timestamp((Long) value)));
							} else if (CriteriaOperator.LESSER_OR_EQUAL.eq(operator)) {
								predicates.add(cb.lessThanOrEqualTo(root.get(Registration_.registeredTime), new Timestamp((Long) value)));
							} else if (operator == null) {
								predicates.add(cb.equal(root.get(Registration_.registeredTime), new Timestamp((Long) value)));
							}
						}
					}
				}
				return cb.and(predicates.toArray(new Predicate[]{}));
			}
		};
	}

}
