package com.nhnent.prereg.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.nhnent.prereg.web.view.SimpleCsvView;
import com.nhnent.prereg.web.view.CsvViewResolver;

@Configuration
public class PreRegMvcConfigurer extends WebMvcConfigurerAdapter {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("admin/login");
		registry.addViewController("/admin").setViewName("admin/home");
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.viewResolver(csvViewResolver());
	}
	
	private ViewResolver csvViewResolver() {
		return new CsvViewResolver();
	}
	
	@Bean
	public View simpleJacksonCsvView() {
		return new SimpleCsvView();
	}

}
