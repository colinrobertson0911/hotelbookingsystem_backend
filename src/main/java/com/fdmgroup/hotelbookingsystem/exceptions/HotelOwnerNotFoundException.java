package com.fdmgroup.hotelbookingsystem.exceptions;

public class HotelOwnerNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1001L;
	
	public HotelOwnerNotFoundException(Long id) {
		super("Could not find hotelOwner " + id);
	}

}
