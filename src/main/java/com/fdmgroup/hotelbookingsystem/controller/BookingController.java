package com.fdmgroup.hotelbookingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fdmgroup.hotelbookingsystem.exceptions.InvalidDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.hotelbookingsystem.exceptions.BookingNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.BookingRequest;
import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@PostMapping("/BookingSubmit")
	public ResponseEntity <HttpStatus> bookingSubmit(@RequestBody BookingRequest bookReq) {
		Bookings bookings = createBookings(bookReq);
		bookings.setRoomType(bookReq.getRoomType());
		BigDecimal extraCosts = bookings.getExtras().getPrice();
		bookings.setExtrasPrice(extraCosts);
		BigDecimal finalTotal = bookingService.calculateTotalPrice(bookings);
		bookings.setTotalPrice(finalTotal);
		try {
			bookingService.save(bookings);
		} catch(DataIntegrityViolationException e){
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	private Bookings createBookings(BookingRequest bookReq) {;
		LocalDate checkin = LocalDate.parse(bookReq.getCheckInDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate checkout = LocalDate.parse(bookReq.getCheckOutDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Bookings bookings = new Bookings(bookReq.getRoomType(),
				bookReq.getHotel(),
				checkin,
			    checkout,
			    new BigDecimal(0),
			    new BigDecimal(0),
			    new BigDecimal(0), 
			    Extras.NO_EXTRAS);
		if (checkout.isBefore(checkin)){
			throw new InvalidDateException(checkout);
		}
		return bookings;
	}

	@GetMapping("/BookingConfirmation/{bookingId}")
	public ResponseEntity<Bookings> bookingConfirmationSubmit(@PathVariable("bookingId") long bookingId) {
		Optional<Bookings> booking = bookingService.retrieveOne(bookingId);
		if (booking.isPresent()) {
			return new ResponseEntity<>(booking.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

}
