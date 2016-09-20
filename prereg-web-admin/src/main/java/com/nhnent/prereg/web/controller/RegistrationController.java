package com.nhnent.prereg.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nhnent.prereg.entity.Registration;
import com.nhnent.prereg.service.RegistrationService;
import com.nhnent.prereg.support.GridRequest;
import com.nhnent.prereg.support.GridResponse;
import com.nhnent.prereg.web.dto.CsvRegistration;

@Controller
@RequestMapping("/regs")
public class RegistrationController {
	private RegistrationService registrationService;
	
	@Autowired
	public RegistrationController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@RequestMapping(value = "/json", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<GridResponse> getRegistrations(@Valid @RequestBody GridRequest data) {
		Page<Registration> page = registrationService.findAll(data);
		GridResponse grid = new GridResponse();
		grid.setDraw(data.getDraw());
		grid.setRecordsTotal(page.getTotalElements());
		grid.setRecordsFiltered(page.getTotalElements());
		grid.setData(page.getContent());
		return new ResponseEntity<GridResponse>(grid, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/csv", method = RequestMethod.GET, produces = {"application/csv"})
	public ModelAndView test(@Valid @ModelAttribute GridRequest data) {
		ModelAndView model = new ModelAndView();
		Page<Registration> page = registrationService.findAll(data);
		List<CsvRegistration> csvData = new ArrayList<CsvRegistration>();
		for (Registration registration : page.getContent()) {
			csvData.add(CsvRegistration.valueOf(registration));
		}
		model.addObject("class", CsvRegistration.class);
		model.addObject("header", null);
		model.addObject("data", csvData);
		model.setViewName("csv_");
		return model;
	}

}
