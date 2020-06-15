package com.fdmgroup.hotelbookingsystem.controller;


import com.fdmgroup.hotelbookingsystem.exceptions.HotelOwnerNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {



	@Autowired
	UserService userService;

	@Autowired
	HotelService hotelService;

	@GetMapping("/AllOwners")
	public ResponseEntity<List<User>> hotelOwners() {
		List<User> users = userService.findAll();
		List<User> owners = new ArrayList<>();
		for (User user : users){
			if (user.getRole().equals("HOTELOWNER")){
				owners.add(user);
			}
		}
		return new ResponseEntity<>(owners, HttpStatus.OK);
	}
	
	@PostMapping("/addHotelOwner")
	public ResponseEntity <HttpStatus> add(@RequestBody User user) {
		user.setRole("HOTELOWNER");
		try {
			userService.save(user);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}
		return ResponseEntity.ok(HttpStatus.CREATED);
	}


	@GetMapping("/SeeHotelOwner/{username}")
	public ResponseEntity<User> getHotelOwner(@PathVariable("username")String username){
		User user = userService.findByUsername(username);
		try {
			user.getUsername();
		} catch (HotelOwnerNotFoundException ex){
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);

//		if (user.getUsername() == null){
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PutMapping("/EditHotelOwnerSubmit")
	public ResponseEntity<User> hotelOwnersUpdated(@RequestBody User user) {
		return ResponseEntity.ok(userService.save(user));
	}

	@GetMapping("/AllHotels")
	public ResponseEntity<List<Hotel>> allHotels() {
		return ResponseEntity.ok(hotelService.findAll());
	}

	@GetMapping("/AllUsers")
	public ResponseEntity<List<User>> allUsers() {
		return ResponseEntity.ok(userService.findAll());
	}


}
