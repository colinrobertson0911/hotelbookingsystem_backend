package com.fdmgroup.hotelbookingsystem.exceptions;

public class HotelCityNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1003L;
	
	public HotelCityNotFoundException(String city) {
		super("Could not find hotels in " + city);
	}

}
