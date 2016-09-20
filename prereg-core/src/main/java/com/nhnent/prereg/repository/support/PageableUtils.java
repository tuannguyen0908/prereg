package com.nhnent.prereg.repository.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.nhnent.prereg.support.GridRequest.SortedColumn;

public class PageableUtils {
	public static final int DEFAULT_START = 0;
	public static final int DEFAULT_SIZE = Integer.MAX_VALUE;
	
	public static <T> Pageable get(int start, int length, List<SortedColumn> columns) {
		List<Sort.Order> orders = new ArrayList<Sort.Order>();
		if (columns != null) {
			for (SortedColumn column : columns) {
				orders.add(new Sort.Order(column.getDir().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, column.getColumn()));
			}
		}
		Sort sort = orders.isEmpty() ? null : new Sort(orders);
		
		if (start <= 0) {
			start = DEFAULT_START;
		}
		
		if (length <= 0) {
			length = DEFAULT_SIZE;
		}
		return new PageRequest(start, length, sort);
	}

}
