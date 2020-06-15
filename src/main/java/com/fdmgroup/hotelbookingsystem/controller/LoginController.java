package com.fdmgroup.hotelbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.AuthenticationResponse;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.JWTservice;
import com.fdmgroup.hotelbookingsystem.services.MyUserDetailsService;
import com.fdmgroup.hotelbookingsystem.services.UserService;


@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {

	public static final String SESSION_ATTRIBUTE_ADMIN = "ADMIN";
	public final static String SESSION_ATTRIBUTE_HOTELOWNER = "HOTELOWNER";
	public final static String SESSION_ATTRIBUTE_HOTELOWNERID = "HOTELOWNERID";

	@Autowired
	UserService userService;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTservice jwtService;

	@GetMapping("LoginUserSubmit/{username}")
	public ResponseEntity <User> loginUser(@PathVariable("username") String username) {
		User user = userService.findByUsername(username).get();
		return ResponseEntity.ok(user);
	}
	
	@PostMapping(value="/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authRequest) throws Exception {

		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("incorrect Username or password", e);
		}
	
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtService.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
