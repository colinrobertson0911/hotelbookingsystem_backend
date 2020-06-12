package com.fdmgroup.hotelbookingsystem.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
		Date checkin = new Date();
		System.out.println("LOGGING LINE : "+ checkin.toString());
		System.out.println("LOGGING LINE : "+ bookReq.getCheckInDate());
		System.out.println("LOGGING LINE : "+ bookReq.toString());
		checkin.parse(bookReq.getCheckInDate());
		Date checkout = new Date();
		checkout.parse(bookReq.getCheckOutDate());
		Bookings bookings = new Bookings(bookReq.getRoomType(),
				bookReq.getHotel(),
				checkin.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate(),
			    checkout.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate(),
			    new BigDecimal(0) ,
			    new BigDecimal(0),
			    new BigDecimal(0), 
			    Extras.NO_EXTRAS);
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

	@GetMapping("/BookingConfirmation/{checkInDate},{hotel}")
	public ResponseEntity<List<Bookings>> bookingConfirmationSubmit(@PathVariable("checkInDate")@DateTimeFormat(pattern = "yyyy-MM-dd") String checkInDateString, @PathVariable("hotel") String hotel) {
		LocalDate checkInDate = LocalDate.parse(checkInDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		List<Bookings> bookings = bookingService.findBookingsByCheckInDate(checkInDate, hotel);
		if (bookings.isEmpty()) {
			return new ResponseEntity<List<Bookings>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Bookings>>(bookings, HttpStatus.OK);
	}

}
