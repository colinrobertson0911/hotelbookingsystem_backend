package com.fdmgroup.hotelbookingsystem.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import com.fdmgroup.hotelbookingsystem.services.UserService;

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
	public ResponseEntity<List<User>> hotelOwners(@RequestParam("page")int page, @RequestParam("size")int size) {
		return ResponseEntity.ok( userService.findAllHotelOwners(page, size));
	}

	@PostMapping("/addHotelOwner")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public User addHotelOwner(@RequestBody @Valid AuthenticationRequest authRequest) {
		return userSecurityService.addHotelOwner(authRequest.getUsername(), authRequest.getPassword(), authRequest.getFirstName(), authRequest.getLastName()).orElseThrow(() ->
				new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}


	@GetMapping("/SeeHotelOwner/{username}")
	@PreAuthorize("hasRole('ROLE_HOTELOWNER') || hasRole('ROLE_ADMIN')")
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
	public ResponseEntity<Page<Hotel>> allHotels(@RequestParam("page")int page, @RequestParam("size")int size) {
		return ResponseEntity.ok(hotelService.findAll(page,size));
	}

	@GetMapping("/AllUsers")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Page<User>> allUsers(@RequestParam("page")int page, @RequestParam("size")int size) {
		return ResponseEntity.ok(userService.findAll(page, size));
	}


}
