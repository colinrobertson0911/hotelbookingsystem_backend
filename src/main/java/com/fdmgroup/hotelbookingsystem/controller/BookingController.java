package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.Booking;
import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.CustomerService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:4200")
public class BookingController {

	@Autowired
	BookingService bookingService;
	@Autowired
	RoomService roomService;
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/BookingSubmit")
	public ResponseEntity <Booking> bookingSubmit(@RequestBody Booking booking, Principal principal) {
		Optional<Customer> optional = customerService.findByUsername(principal.getName());
		if (optional.isEmpty()){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Customer customer = optional.get();
		LocalDate checkin = booking.getCheckInDate();
		LocalDate checkout = booking.getCheckOutDate();
		Booking bookings = new Booking(booking.getRoomType(),
				booking.getHotel(),
				checkin,
				checkout,
				new BigDecimal(0),
				new BigDecimal(0),
				new BigDecimal(0),
				Extras.NO_EXTRAS);
		if (checkout.isBefore(checkin)){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		bookings.setRoomType(booking.getRoomType());
		Room room = roomService.findByRoomType(booking.getRoomType()).get(0);
		bookings.setRoomPrice(room.getPrice());
		bookings.setExtrasPrice(booking.getExtras().getPrice());
		bookings.setExtras(booking.getExtras());

		BigDecimal finalTotal = bookingService.calculateTotalPrice(bookings);
		bookings.setTotalPrice(finalTotal);

		try {
			customer.getBookings().add(bookings);
			bookingService.save(bookings);
			customerService.save(customer);

		} catch(DataIntegrityViolationException e){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(bookings, HttpStatus.CREATED);
	}

	@GetMapping("/BookingConfirmation/{bookingId}")
	public ResponseEntity<Booking> bookingConfirmationSubmit(@PathVariable("bookingId") long bookingId) {
		Optional<Booking> booking = bookingService.retrieveOne(bookingId);
		if (booking.isPresent()) {
			return new ResponseEntity<>(booking.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

}
