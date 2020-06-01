package com.fdmgroup.hotelbookingsystem.exceptions;

public class BookingNotFoundException extends RuntimeException{

	
	private static final long serialVersionUID = 1004L;
	
	public BookingNotFoundException(Long bookingID) {
		super("Could not find Booking " + bookingID);
	}
}
