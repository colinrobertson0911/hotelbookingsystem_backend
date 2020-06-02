package com.fdmgroup.hotelbookingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;

@RestController
@RequestMapping("/hotelOwner")
@CrossOrigin(origins = "http://localhost:4200")
public class HotelOwnerController {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@Autowired
	BookingService bookingService;


	@PostMapping("/AddHotelSubmit/{hotelOwnerId}")
	public ResponseEntity<HttpStatus> addHotelSubmit(@PathVariable("hotelOwnerId")long hotelOwnerId, @RequestBody Hotel hotel) {
		Optional<Hotel> hotelFromDB = hotelService.findByAddress(hotel.getAddress());
		if (hotelFromDB.isPresent()) {
			return new ResponseEntity<HttpStatus> (HttpStatus.IM_USED);
		}
		if (hotel.isAirportTransfers() != true) {
			hotel.setTransferPrice(0);
		}
		try {
			hotelService.save(hotel);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}

	@PutMapping("/EditHotelSubmit/{hotelOwnerId}")
	public ResponseEntity<Hotel> editHotelSubmit(@PathVariable("hotelOwnerId") long hotelOwnerId, @RequestBody Hotel hotel) {
		return ResponseEntity.ok(hotelService.save(hotel));
	}

	@GetMapping("/AllBookings/{hotelOwnerId}")
	public ResponseEntity<List<Bookings>> allBookings(@PathVariable("hotelOwnerId") Long hotelOwnerId) {
		return ResponseEntity.ok(bookingService.findAll());
	}


	@PostMapping("/AddNewRoomTypeSubmit/{hotelOwnerId}")
	public ResponseEntity<HttpStatus> newRoomTypeSubmit(@PathVariable("hotelOwnerId")long hotelOwnerId, @RequestBody Room room) {
		Optional<Room> roomFromDB = roomService.findByRoomTypeAndPrice(room.getRoomType(), room.getPrice());
		if (roomFromDB.isPresent()) {
			return new ResponseEntity<HttpStatus> (HttpStatus.IM_USED);
		}
		try {
			roomService.save(room);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}
}
