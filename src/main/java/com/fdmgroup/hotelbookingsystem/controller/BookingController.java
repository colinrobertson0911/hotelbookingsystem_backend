package com.fdmgroup.hotelbookingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.services.BookingService;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {
	
	@Autowired
	BookingService bookingService;

	@Autowired
	RoomService roomService;
	
	@PostMapping("/BookingSubmit")
	public ResponseEntity <HttpStatus> bookingSubmit(@RequestBody Bookings booking) {
		LocalDate checkin = booking.getCheckInDate();
		LocalDate checkout = booking.getCheckOutDate();
		Bookings bookings = new Bookings(booking.getRoomType(),
				booking.getHotel(),
				checkin,
				checkout,
				new BigDecimal(0),
				new BigDecimal(0),
				new BigDecimal(0),
				Extras.NO_EXTRAS);
		if (checkout.isBefore(checkin)){
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}

		bookings.setRoomType(booking.getRoomType());
		Room room = roomService.findByRoomType(booking.getRoomType()).get(0);
		bookings.setRoomPrice(room.getPrice());
		bookings.setExtrasPrice(booking.getExtras().getPrice());
		bookings.setExtras(booking.getExtras());


		BigDecimal finalTotal = bookingService.calculateTotalPrice(bookings);
		bookings.setTotalPrice(finalTotal);
		try {
			bookingService.save(bookings);
		} catch(DataIntegrityViolationException e){
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
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
