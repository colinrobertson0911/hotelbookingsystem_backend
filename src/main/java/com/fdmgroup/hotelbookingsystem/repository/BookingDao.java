package com.fdmgroup.hotelbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.hotelbookingsystem.model.Bookings;

public interface BookingDao extends JpaRepository<Bookings, Long> {

	Bookings findByBookingId(long bookingId);

}
