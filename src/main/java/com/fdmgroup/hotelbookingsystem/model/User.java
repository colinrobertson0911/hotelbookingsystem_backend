package com.fdmgroup.hotelbookingsystem.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
	@SequenceGenerator(name = "user_gen", sequenceName = "USER_SEQ", allocationSize = 1)
	private long userId;

	@Column(nullable = false, length = 50, unique = true)
	private String username;

	@Column(nullable = false, length = 50)
	private String password;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String role;
	
	private String token;



	public User() {
		super();
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User(String username, String password, String firstName, String lastName, String role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return getUserId() == user.getUserId() &&
				getUsername().equals(user.getUsername()) &&
				getPassword().equals(user.getPassword()) &&
				getFirstName().equals(user.getFirstName()) &&
				getLastName().equals(user.getLastName()) &&
				getRole().equals(user.getRole());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUserId(), getUsername(), getPassword(), getFirstName(), getLastName(), getRole());
	}

	@Override
	public String toString() {
		return "User{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", role='" + role + '\'' +
				'}';
	}
}
