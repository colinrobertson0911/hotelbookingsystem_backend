package com.fdmgroup.hotelbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.hotelbookingsystem.model.Bookings;

public interface BookingDao extends JpaRepository<Bookings, Long> {

	Optional<Bookings> findByBookingId(long bookingId);

}
