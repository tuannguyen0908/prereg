package com.nhnent.prereg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Mobile number is already registered")
public class MobileAlreadyExistedException extends RuntimeException {

	/**
	 * Unique id of serialized object
	 */
	private static final long serialVersionUID = -274515696094997257L;

}
