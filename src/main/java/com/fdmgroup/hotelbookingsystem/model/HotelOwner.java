package com.fdmgroup.hotelbookingsystem.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class HotelOwner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotelOwner_gen")
	@SequenceGenerator(name = "hotelOwner_gen", sequenceName = "HOTELOWNER_SEQ", allocationSize = 1)
	private long hotelOwnerId;

	@Column(nullable = false, length = 50, unique = true)
	private String username;

	@Column(nullable = false, length = 50)
	private String password;

	@Column(nullable = false, length = 60)
	private String email;

	@Column
	private String name;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "hotelId")
	private List<Hotel> hotel;

	public HotelOwner() {
		super();
	}

	public HotelOwner(String username, String password, String email, String name, List<Hotel> hotel) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.hotel = hotel;
	}

	public long getHotelOwnerId() {
		return hotelOwnerId;
	}

	public void setHotelOwnerId(long hotelOwnerId) {
		this.hotelOwnerId = hotelOwnerId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Hotel> getHotel() {
		return hotel;
	}

	public void setHotel(List<Hotel> hotel) {
		this.hotel = hotel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + (int) (hotelOwnerId ^ (hotelOwnerId >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HotelOwner other = (HotelOwner) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (hotelOwnerId != other.hotelOwnerId)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HotelOwner [hotelOwnerId=" + hotelOwnerId + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", name=" + name + ", hotel=" + hotel + "]";
	}

}