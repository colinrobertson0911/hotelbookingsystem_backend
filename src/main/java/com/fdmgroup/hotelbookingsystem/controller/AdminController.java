package com.fdmgroup.hotelbookingsystem.controller;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

	@GetMapping("/SeeHotelOwner/{hotelOwnerId}")
	public ResponseEntity <HotelOwner> getHotelOwner(@PathVariable("hotelOwnerId") long hotelOwnerId) {
		Optional<HotelOwner> hotelOwner = hotelOwnerService.retrieveOne(hotelOwnerId);
		return ResponseEntity.ok(hotelOwner.get());
		
	}

	@PostMapping("EditHotelOwnerSubmit")
	public HotelOwner hotelOwnersUpdated(@RequestBody HotelOwner hotelOwner) {
		return hotelOwnerService.save(hotelOwner);
	}

	@GetMapping("/AllHotels")
	public ResponseEntity<List<Hotel>> allHotels() {
		return ResponseEntity.ok(hotelService.findAll());
	}

	@GetMapping("/VerifyHotel/{hotelId}")
	public ResponseEntity <Hotel> verifyHotel(@PathVariable("hotelId") long hotelId) {
		Optional<Hotel> hotel = hotelService.retrieveOne(hotelId);
		return ResponseEntity.ok(hotel.get());
	}

	@PostMapping("/VerifyHotelSubmit")
	public Hotel newHotel(@RequestBody Hotel hotel) {
		return hotelService.save(hotel);
		
	}
	

}
