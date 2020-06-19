package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;


@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {

	@Autowired
	private UserSecurityService userSecurityService;


	@PostMapping("/LoginUser")
	@ResponseStatus(HttpStatus.OK)
	public String loginUser(@RequestBody @Valid AuthenticationRequest authRequest) {
		return userSecurityService.signin(authRequest.getUsername(), authRequest.getPassword()).orElseThrow(()->
				new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
	}

	@PostMapping("/RegisterUser")
	@ResponseStatus(HttpStatus.CREATED)
	public User registerUser(@RequestBody @Valid AuthenticationRequest authRequest) {
		return userSecurityService.signup(authRequest.getUsername(), authRequest.getPassword(), authRequest.getFirstName(), authRequest.getLastName()).orElseThrow(() ->
				new HttpServerErrorException(HttpStatus.BAD_REQUEST, "User already exists"));
	}

}
