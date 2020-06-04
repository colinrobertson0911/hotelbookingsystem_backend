package com.fdmgroup.hotelbookingsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(BookingNotFoundException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	String bookingNotFoundHandler(BookingNotFoundException ex) {
		return ex.getMessage();
	}

}
