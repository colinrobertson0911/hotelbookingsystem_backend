package com.fdmgroup.hotelbookingsystem.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.repository.BookingDao;

@Service
public class BookingService {

	@Autowired
	BookingDao bookingDao;

	@Autowired
	HotelService hotelService;

	public List<Bookings> findAll() {
		return bookingDao.findAll();
	}

	public Bookings save(Bookings booking) {
		return bookingDao.save(booking);
	}

	public boolean findRoomAvailability(Hotel hotel) {
		int numOfRooms = hotel.getNumOfRooms();
		List<Bookings> bookings = hotel.getBookings();
		if (bookings.size() < numOfRooms) {
			return true;
		} else {
			return false;
		}

	}

	public BigDecimal calculateTotalPrice(Bookings booking) {

		LocalDate checkInDate = booking.getCheckInDate();
		LocalDate checkOutDate = booking.getCheckOutDate();
		long stayDuration = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
		BigDecimal totalPrice;
		if (stayDuration > 5) {
			BigDecimal stayDurationBigDec = BigDecimal.valueOf(stayDuration);
			BigDecimal roomPrice = booking.getRoomPrice();
			BigDecimal extrasPrice = booking.getExtrasPrice();
			totalPrice = (roomPrice.multiply(stayDurationBigDec).add(extrasPrice).subtract(roomPrice));
		} else {
			BigDecimal stayDurationBigDec = BigDecimal.valueOf(stayDuration);
			BigDecimal roomPrice = booking.getRoomPrice();
			BigDecimal extrasPrice = booking.getExtrasPrice();
			totalPrice = (roomPrice.multiply(stayDurationBigDec).add(extrasPrice));
		}

		return totalPrice;
	}

	public Optional<Bookings> retrieveOne(long bookingId) {
		return bookingDao.findByBookingId(bookingId);
	}

	public void deleteById(long bookingId) {
		bookingDao.deleteById(bookingId);
	}

}
