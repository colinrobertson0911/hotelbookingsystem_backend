package com.fdmgroup.hotelbookingsystem.exceptions;

public class HotelNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1002L;
	
	public HotelNotFoundException(String hotelName) {
		super("Could not find hotel " + hotelName);
	}

}
