package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.exceptions.HotelNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.services.CustomerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PreAuthorize("hasRole('ROLE_VIEWER')")
	@GetMapping("/Details/{user}")
	public ResponseEntity<Customer> userDetails(@PathVariable("user") String user) {
		Optional<Customer> customer = customerService.findByUsername(user);
		if (customer.isEmpty()) {
			return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
	}

}
