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

import com.fdmgroup.hotelbookingsystem.exceptions.HotelNotFoundException;
import com.fdmgroup.hotelbookingsystem.exceptions.HotelOwnerNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;

@RestController
@RequestMapping("/hotelbookingsystem/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@GetMapping("/AllOwners")
	public ResponseEntity<List<HotelOwner>> hotelOwners() {
		return ResponseEntity.ok(hotelOwnerService.findAll());
	}
	
	@PostMapping("/addHotelOwner")
	public ResponseEntity <HttpStatus> add(@RequestBody HotelOwner hotelOwner) {
		try {
			hotelOwnerService.save(hotelOwner);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}
	

	@GetMapping("/SeeHotelOwner/{hotelOwnerId}")
	public HotelOwner getHotelOwner(@PathVariable("hotelOwnerId") long hotelOwnerId) {
		return ((Optional<HotelOwner>) hotelOwnerService.retrieveOne(hotelOwnerId)).orElseThrow(() 
				-> new HotelOwnerNotFoundException(hotelOwnerId));
		
	}

	@PutMapping("EditHotelOwnerSubmit")
	public ResponseEntity<HotelOwner> hotelOwnersUpdated(@RequestBody HotelOwner hotelOwner) {
		return ResponseEntity.ok(hotelOwnerService.save(hotelOwner));
	}

	@GetMapping("/AllHotels")
	public ResponseEntity<List<Hotel>> allHotels() {
		return ResponseEntity.ok(hotelService.findAll());
	}

	@GetMapping("/VerifyHotel/{hotelId}")
	public Hotel verifyHotel(@PathVariable("hotelId") long hotelId) {
		return ((Optional<Hotel>) hotelService.retrieveOne(hotelId)).orElseThrow(()
				-> new HotelNotFoundException(hotelId));
	}

	
	@PostMapping("/VerifyHotelSubmit")
	public ResponseEntity <HttpStatus> addHotel(@RequestBody Hotel hotel) {
		try {
			hotelService.save(hotel);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}
	

}
