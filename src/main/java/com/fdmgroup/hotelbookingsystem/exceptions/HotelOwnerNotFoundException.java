package com.fdmgroup.hotelbookingsystem.exceptions;

public class HotelOwnerNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1001L;
	
	public HotelOwnerNotFoundException(String username) {
		super("Could not find hotelOwner " + username);
	}

}
