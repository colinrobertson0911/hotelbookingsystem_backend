package com.fdmgroup.hotelbookingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HotelOwnerNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(HotelOwnerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String ownerNotFoundHandler(HotelOwnerNotFoundException ex) {
		return ex.getMessage();
	}
	
}
