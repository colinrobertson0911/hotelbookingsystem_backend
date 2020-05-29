package com.fdmgroup.hotelbookingsystem.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
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
