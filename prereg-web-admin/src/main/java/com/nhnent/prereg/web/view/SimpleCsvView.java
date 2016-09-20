package com.nhnent.prereg.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleCsvView extends AbstractCsvView {

	@Override
	protected void buildCsvDocumentData(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		this.header = (String[]) model.get(headerKey);
		this.data = model.get(dataKey);
	}

}
