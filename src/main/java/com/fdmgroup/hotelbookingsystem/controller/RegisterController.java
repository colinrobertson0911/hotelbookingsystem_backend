package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationResponse;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.JWTservice;
import com.fdmgroup.hotelbookingsystem.services.MyUserDetailsService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:4200")
public class RegisterController {

    @Autowired
    UserService userService;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @Autowired
    private JWTservice jwtService;

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

