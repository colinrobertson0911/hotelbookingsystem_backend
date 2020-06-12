package com.fdmgroup.hotelbookingsystem.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.hotelbookingsystem.model.Bookings;

public interface BookingDao extends JpaRepository<Bookings, Long> {

	Optional<Bookings> findByBookingId(long bookingId);

	List<Bookings> findByCheckInDateAndHotel(LocalDate checkInDate, String hotel);

//	@Query(value = "SELECT * FROM BOOKINGS INNER JOIN HOTELOWNER_BOOKINGS ON BOOKINGS.BOOKINGID = HOTELOWNER_BOOKINGS.BOOKINGID INNER JOIN HOTELOWNER ON HOTELOWNER_BOOKINGS.HOTELOWNERID = HOTELOWNER.HOTELOWNERID", nativeQuery = true)
//    List<Bookings> findAllByUsername(String username);
}
