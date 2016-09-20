package com.nhnent.prereg.web.view;

import java.util.Locale;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.Ordered;
import org.springframework.web.context.support.WebApplicationObjectSupport;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class CsvViewResolver extends WebApplicationObjectSupport implements ViewResolver, Ordered {

	private String viewBeanName = "simpleJacksonCsvView";
	
	private String viewNameprefix = "csv_";
	
	private int order = Integer.MAX_VALUE;  // default: same as non-Ordered
	
	@Override
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}

	public String getViewBeanName() {
		return viewBeanName;
	}

	public void setViewBeanName(String viewBeanName) {
		this.viewBeanName = viewBeanName;
	}

	public String getViewNameprefix() {
		return viewNameprefix;
	}

	public void setViewNameprefix(String viewNameprefix) {
		this.viewNameprefix = viewNameprefix;
	}

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		try {
			if (viewName.startsWith(viewNameprefix)) {
				// delegate content generating to View
				return getApplicationContext().getBean(viewBeanName, View.class);
			}
		} catch (NoSuchBeanDefinitionException ex) {
			// to allow for ViewResolver chaining
			return null;
		}
		return null;
	}

}
