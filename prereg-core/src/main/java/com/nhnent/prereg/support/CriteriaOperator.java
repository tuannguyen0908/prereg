package com.nhnent.prereg.support;

public enum CriteriaOperator {
	EQUAL(0),
	GREATER(1),
	GREATER_OR_EQUAL(2),
	LESSER(3),
	LESSER_OR_EQUAL(4),
	
	LIKE(5),
	IS_NULL(6),
	IS_NOT_NULL(7),
	;
	
	public Integer id;
	private CriteriaOperator(int id) {
		this.id = id;
	}
	
	public static CriteriaOperator valueOf(int id) throws IllegalArgumentException {
		if (id == EQUAL.id) {
			return EQUAL;
		} else if (id == GREATER.id) {
			return GREATER;
		} else if (id == GREATER_OR_EQUAL.id) {
			return GREATER_OR_EQUAL;
		} else if (id == LESSER.id) {
			return LESSER;
		} else if (id == LESSER_OR_EQUAL.id) {
			return LESSER_OR_EQUAL;
		} else if (id == LIKE.id) {
			return LIKE;
		} else if (id == IS_NULL.id) {
			return IS_NULL;
		} else if (id == IS_NOT_NULL.id) {
			return IS_NOT_NULL;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean eq(CriteriaOperator op) {
		if (op != null) {
			if (this.id.equals(op.id)) {
				return true;
			}
		}
		return false;
	}

}
