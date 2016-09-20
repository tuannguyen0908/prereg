package com.nhnent.prereg.support;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GridRequest {
	private int draw;
	private int length;
	private int start;
	private Boolean eagerly;
	private String keyword;
	private List<SortedColumn> sorts;
	private List<FilteredColumn> filters;

	public GridRequest() {
		eagerly = Boolean.FALSE;
	}
	
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public Boolean getEagerly() {
		return eagerly;
	}

	public void setEagerly(Boolean eagerly) {
		this.eagerly = eagerly;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		if (keyword != null) {
			this.keyword = keyword.trim();
		}
	}

	public List<SortedColumn> getSorts() {
		return sorts;
	}

	public void setSorts(List<SortedColumn> sorts) {
		this.sorts = sorts;
	}
	
	public Map<String, String> getSortedColumns() {
		Map<String, String> columns = new LinkedHashMap<String, String>();
		if (sorts != null) {
			for (SortedColumn column : sorts) {
				columns.put(column.getColumn(), column.getDir());
			}
		}
		return columns;
	}
	
	public List<FilteredColumn> getFilters() {
		return filters;
	}

	public void setFilters(List<FilteredColumn> filters) {
		this.filters = filters;
	}
	
	public Map<String, Object> getFilteredColumns() {
		Map<String, Object> columns = new LinkedHashMap<String, Object>();
		if (filters != null) {
			for (FilteredColumn column : filters) {
				if (column.getOperator() != null) {
					columns.put(column.getColumn(), column);
				} else {
					columns.put(column.getColumn(), column.getValue());
				}
			}
		}
		return columns;
	}
	
	/*public List<CriteriaEntry> getFilterEntries() {
		List<CriteriaEntry> columns = new ArrayList<CriteriaEntry>();
		if (filters != null) {
			for (FilteredColumn column : filters) {
				if (column.getOperator() != null) {
					columns.add(new CriteriaEntry(column.getColumn(), column));
				} else {
					columns.add(new CriteriaEntry(column.getColumn(), column.getValue()));
				}
			}
		}
		return columns;
	}*/
	
	public List<CriteriaEntry> getFilterEntries() {
		List<CriteriaEntry> criterias = new ArrayList<CriteriaEntry>();
		if (filters != null) {
			Integer operator = null;
			CriteriaOperator criteriaOperator = null;
			for (FilteredColumn column : filters) {
				if (column != null) {
					operator = column.getOperator();
					if (operator != null) {
						criteriaOperator = CriteriaOperator.valueOf(operator);
					} else {
						criteriaOperator = null;
					}
					//if (criteriaOperator != null) {
						try {
							criterias.add(new CriteriaEntry(column.getColumn(), column.getValue(), criteriaOperator));
						} catch (IllegalArgumentException ignore) {}
					//}
				}
			}			
		}
		return criterias;
	}

	public static class SortedColumn {
		private String column;
		private String dir;
		
		public SortedColumn() {}

		public String getColumn() {
			return column;
		}

		public void setColumn(String column) {
			this.column = column;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}
		
	}
	
	public static class FilteredColumn {
		private String column;
		private Object value;
		private Integer operator;
		
		public FilteredColumn() {
			
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

		public Integer getOperator() {
			return operator;
		}

		public void setOperator(Integer operator) {
			if (operator != null) {
				if (operator.equals(FilteredOperator.EQUAL.id)) {
					this.operator = FilteredOperator.EQUAL.id;
				} else if (operator.equals(FilteredOperator.GREATER.id)) {
					this.operator = FilteredOperator.GREATER.id;
				} else if (operator.equals(FilteredOperator.GREATER_OR_EQUAL.id)) {
					this.operator = FilteredOperator.GREATER_OR_EQUAL.id;
				} else if (operator.equals(FilteredOperator.LESSER.id)) {
					this.operator = FilteredOperator.LESSER.id;
				} else if (operator.equals(FilteredOperator.LESSER_OR_EQUAL.id)) {
					this.operator = FilteredOperator.LESSER_OR_EQUAL.id;
				} else if (operator.equals(FilteredOperator.LIKE.id)) {
					this.operator = FilteredOperator.LIKE.id;
				} else if (operator.equals(FilteredOperator.IS_NULL.id)) {
					this.operator = FilteredOperator.IS_NULL.id;
				} else if (operator.equals(FilteredOperator.IS_NOT_NULL.id)) {
					this.operator = FilteredOperator.IS_NOT_NULL.id;
				} else {
					this.operator = FilteredOperator.EQUAL.id;
				}
			}
		}
		
	}
	
	public static enum FilteredOperator {
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
		private FilteredOperator(Integer id) {
			this.id = id;
		}
	}

}
