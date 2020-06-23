package com.fdmgroup.hotelbookingsystem.repository;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingDao extends JpaRepository<Bookings, Long> {

	Optional<Bookings> findByBookingId(long bookingId);

}
