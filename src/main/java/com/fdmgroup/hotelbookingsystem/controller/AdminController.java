package com.fdmgroup.hotelbookingsystem.controller;


import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
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
	UserSecurityService userSecurityService;

	@Autowired
	HotelService hotelService;

	@GetMapping("/AllOwners")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> hotelOwners() {
		List<User> users = userService.findAll();
		List<User> owners = new ArrayList<>();
		for (User user : users){
			if (user.getRoles().equals("HOTELOWNER")){
				owners.add(user);
			}
		}
		return new ResponseEntity<>(owners, HttpStatus.OK);
	}

	@PostMapping("/addHotelOwner")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public User addHotelOwner(@RequestBody @Valid AuthenticationRequest authRequest) {
		return userSecurityService.addHotelOwner(authRequest.getUsername(), authRequest.getPassword(), authRequest.getFirstName(), authRequest.getLastName()).orElseThrow(() ->
				new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}


	@GetMapping("/SeeHotelOwner/{username}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> getHotelOwner(@PathVariable("username")String username){
		Optional<User> user = userService.findByUsername(username);
		if (user.isPresent()){
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}

	@PutMapping("/EditHotelOwnerSubmit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> hotelOwnersUpdated(@RequestBody User user) {
		return ResponseEntity.ok(userService.save(user));
	}

	@GetMapping("/AllHotels")
	public ResponseEntity<List<Hotel>> allHotels() {
		return ResponseEntity.ok(hotelService.findAll());
	}

	@GetMapping("/AllUsers")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<User>> allUsers() {
		return ResponseEntity.ok(userService.findAll());
	}


}
