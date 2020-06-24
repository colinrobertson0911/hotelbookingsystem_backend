package com.fdmgroup.hotelbookingsystem.repository;

import com.fdmgroup.hotelbookingsystem.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingDao extends JpaRepository<Booking, Long> {

	Optional<Booking> findByBookingId(long bookingId);

}
