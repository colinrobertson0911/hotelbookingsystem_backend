package com.fdmgroup.hotelbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserService;

@RestController
@RequestMapping("/hotelbookingsystem/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	public static final String SESSION_ATTRIBUTE_ADMIN = "ADMIN";
	public final static String SESSION_ATTRIBUTE_HOTELOWNER = "HOTELOWNER";
	public final static String SESSION_ATTRIBUTE_HOTELOWNERID = "HOTELOWNERID";

	@Autowired
	UserService userService;

	@GetMapping("/LoginUserSubmit/{userId}")
	public ResponseEntity <User> loginUser(@PathVariable("userId") long userId) {
		User user = userService.retrieveOne(userId).get();
		return ResponseEntity.ok(user);
	}

}
