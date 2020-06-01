package com.fdmgroup.hotelbookingsystem.exceptions;

public class HotelNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1002L;
	
	public HotelNotFoundException(Long id) {
		super("Could not find hotel " + id);
	}

}
