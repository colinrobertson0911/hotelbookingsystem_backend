package com.fdmgroup.hotelbookingsystem.exceptions;

import java.time.LocalDate;

public class InvalidDateException extends RuntimeException {

    private static final long serialVersionUID = 1006L;

//    public BookingNotFoundException(Long bookingID) {
//        super("Could not find Booking " + bookingID);
//    }

    public InvalidDateException(LocalDate checkOutDate) {
        super("Invalid date " + checkOutDate + "Choose date after check in date");
    }

}
