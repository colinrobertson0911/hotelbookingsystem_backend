package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class LoginController {

	@Autowired
	UserSecurityService userSecurityService;

	@Autowired
	UserService userService;


	@PostMapping("/LoginUser")
	@ResponseStatus(HttpStatus.OK)
	public User loginUser(@RequestBody User user) {
		return userSecurityService.signin(user.getUsername(), user.getPassword()).orElseThrow(()->
				new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
	}

	@PostMapping("/RegisterUser")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer registerUser(@RequestBody @Valid AuthenticationRequest authRequest) {
		return userSecurityService.signup(authRequest.getUsername(), authRequest.getPassword(), authRequest.getFirstName(), authRequest.getLastName(), authRequest.getAddress(), authRequest.getEmail()).orElseThrow(() ->
				new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}

	@GetMapping("/Details/{username}")
	public ResponseEntity<User> userDetails(@PathVariable("username") String username) {
		Optional<User> user = userService.findByUsername(username);
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}

}
