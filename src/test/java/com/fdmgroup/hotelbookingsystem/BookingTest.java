package com.fdmgroup.hotelbookingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BookingTest {

	@Autowired
	HotelService hotelService;

	@Autowired
	BookingService bookingService;

	@Test
	public void test_ThatABookingCanBeMAde() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 27);

		Bookings booking = new Bookings();
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);

		int numberBeforeAdding = bookingService.findAll().size();
		bookingService.save(booking);
		int numberAfterAdding = bookingService.findAll().size();
		assertNotEquals(numberBeforeAdding, numberAfterAdding);
	}

	@Test
	public void test_ThatABookingCanBeRetrieved() {
		Hotel hotel = hotelService.retrieveOne(1L);
		List<Bookings> bookings = hotel.getBookings();
		assert (bookings.size() > 0);
	}

	@Test
	public void test_ToSeeAvailability() {
		Hotel hotel = hotelService.retrieveOne(1L);
		boolean booking = bookingService.findRoomAvailability(hotel);
		assertEquals(booking, true);
	}

	@Test
	public void test_ThatPriceTotalCanBeCalculated() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 27);
		Hotel hotel = hotelService.retrieveOne(1L);
		Bookings booking = new Bookings();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		booking.setRoomPrice(new BigDecimal("15.00"));
		booking.setExtras(Extras.AIRPORTTRANSFER);
		booking.setExtrasPrice(new BigDecimal("15.00"));
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);
		assertEquals(totalPrice, new BigDecimal("105.00"));
	}

	@Test
	public void test_ThatPriceTotalCanBeCalculatedLessThanFiveDays() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 22);
		Hotel hotel = hotelService.retrieveOne(1L);
		Bookings booking = new Bookings();
		booking.setRoomType("STANDARD");
		booking.setHotel(hotel.getHotelName());
		booking.setCheckInDate(checkInDate);
		booking.setCheckOutDate(checkOutDate);
		booking.setRoomPrice(new BigDecimal("15.00"));
		booking.setExtras(Extras.AIRPORTTRANSFER);
		booking.setExtrasPrice(new BigDecimal("15.00"));
		BigDecimal totalPrice = bookingService.calculateTotalPrice(booking);
		assertEquals(totalPrice, new BigDecimal("45.00"));
	}

	@Test
	public void test_ThatBookingsCanBeCalledById() {
		Bookings booking = bookingService.retrieveOne(1L);
		long bookingId = booking.getBookingId();
		Bookings bookingFromDatabase = bookingService.retrieveOne(bookingId);
		assertEquals(booking.getBookingId(), bookingFromDatabase.getBookingId());
	}

	@Test
	public void test_DeleteABookingById() {
		Bookings booking = bookingService.retrieveOne(2L);
		long bookingId = booking.getBookingId();
		List<Bookings> listBeforeDelete = bookingService.findAll();
		int listSizeBefore = listBeforeDelete.size();
		bookingService.deleteById(bookingId);
		List<Bookings> listAfterDelete = bookingService.findAll();
		int listSizeAfter = listAfterDelete.size();
		assert (listSizeBefore > listSizeAfter);
	}

}
