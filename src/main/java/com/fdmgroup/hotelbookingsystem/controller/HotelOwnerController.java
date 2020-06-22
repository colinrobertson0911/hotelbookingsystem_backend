package com.fdmgroup.hotelbookingsystem.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@PreAuthorize("hasRole('ROLE_HOTELOWNER')")
public class HotelOwnerController {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@Autowired
	RoomService roomService;

	@Autowired
	BookingService bookingService;


	@PostMapping("/AddHotelSubmit")
	public ResponseEntity<HttpStatus> addHotelSubmit(@RequestBody Hotel hotel) {
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

	@PutMapping("/EditHotelSubmit")
	public ResponseEntity<HttpStatus> editHotelSubmit(@RequestBody Hotel hotel) {
		Optional<Hotel> hotelFromDB = hotelService.findById(hotel.getHotelId());
		if(hotelFromDB.isPresent()){
			hotelService.save(hotel);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}


	@PostMapping("/AddNewRoomTypeSubmit")
	public ResponseEntity<HttpStatus> newRoomTypeSubmit(@RequestBody Room room) {
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
