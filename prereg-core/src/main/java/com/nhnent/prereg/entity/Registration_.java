package com.nhnent.prereg.entity;

import java.sql.Timestamp;

import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel(Registration.class)
public class Registration_ {
	public static volatile SingularAttribute<Registration, String> mobileNo;
	public static volatile SingularAttribute<Registration, String> referredBy;
	public static volatile SingularAttribute<Registration, Timestamp> registeredTime;
	public static volatile SingularAttribute<Registration, Long> numberOfRefer;

}
