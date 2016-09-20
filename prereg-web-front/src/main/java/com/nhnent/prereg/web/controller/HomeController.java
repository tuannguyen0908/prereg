package com.nhnent.prereg.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nhnent.prereg.entity.Registration;
import com.nhnent.prereg.service.RegistrationService;
import com.nhnent.prereg.web.dto.RegistrationRequest;

@Controller
@RequestMapping("/regs")
public class HomeController {
	private RegistrationService registrationService;
	
	@Autowired
	public HomeController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE}, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Registration> register(@Valid @RequestBody RegistrationRequest data) {
		Registration registration = registrationService.register(data.getMobileNo(), data.getReferredMobileNo());
		return new ResponseEntity<Registration>(registration, HttpStatus.OK);
	}

}
