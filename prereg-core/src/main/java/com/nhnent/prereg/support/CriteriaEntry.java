package com.nhnent.prereg.support;

public class CriteriaEntry {
	private String column;
	private Object value;
	private CriteriaOperator operator;
	
	public CriteriaEntry() {
		
	}
	
	public CriteriaEntry(String column, Object value) {
		this.column = column;
		this.value = value;
		this.operator = null;
	}
	
	public CriteriaEntry(String key, Object value, CriteriaOperator operator) {
		this.column = key;
		this.value = value;
		this.operator = operator;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public CriteriaOperator getOperator() {
		return operator;
	}

	public void setOperator(CriteriaOperator operator) {
		this.operator = operator;
	}

}
