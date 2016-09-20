package com.nhnent.prereg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "You can't recommend yourself")
public class IllegalReferMobileException extends RuntimeException {

	/**
	 * Unique id of serialized object
	 */
	private static final long serialVersionUID = -598940075110532628L;

}
