package com.fdmgroup.hotelbookingsystem.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AuthenticationRequest {

	@NotNull
	private String username;

	@NotNull
	@Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
	private String password;

	private String firstName;

	private String lastName;

	public AuthenticationRequest() {

	}

	public AuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public AuthenticationRequest(String username, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	
}
