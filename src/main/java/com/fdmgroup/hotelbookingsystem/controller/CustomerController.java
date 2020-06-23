package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PreAuthorize("hasRole('ROLE_VIEWER')")
	@GetMapping("/Details/{user}")
	public ResponseEntity<Customer> userDetails(@PathVariable("user") String user) {
		Optional<Customer> customer = customerService.findByUsername(user);
		if (customer.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(customer.get(), HttpStatus.OK);
	}

}
