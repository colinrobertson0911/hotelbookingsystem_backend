package com.fdmgroup.hotelbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
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

import java.util.Optional;


@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class LoginController {

	@Autowired
	UserService userService;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTservice jwtService;

	@GetMapping("LoginUserSubmit/{username}")
	public ResponseEntity <User> loginUser(@PathVariable("username") String username) throws Exception {
		Optional<User> user = userService.findByUsername(username);
		if (user.isPresent()){
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PostMapping(value="/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createAuthenticationToken (@RequestBody AuthenticationRequest authRequest) throws Exception {

		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("incorrect Username or password", e);
		}
	
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtService.generateToken(userDetails);
		User user = userService.findByUsername(authRequest.getUsername()).get();
		user.setToken(jwt);
		return ResponseEntity.ok(user);
	}
	//new AuthenticationResponse(jwt)

	@PostMapping("/RegisterUserSubmit")
	public ResponseEntity<?> registerUserSubmit(@RequestBody User user) throws Exception{
		try {
			userService.save(user);
		}catch(DataIntegrityViolationException e) {
			return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
		}

		try {
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("incorrect Username or password", e);
		}

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(user.getUsername());
		final String jwt = jwtService.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}

}
