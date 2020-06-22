package com.fdmgroup.hotelbookingsystem.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_gen")
	@SequenceGenerator(name = "hotel_gen", sequenceName = "HOTEL_SEQ", allocationSize = 1)
	private long hotelId;

	@Column(nullable = false)
	@Size(min = 1, message = "Hotel name must be at least one character")
	private String hotelName;

	@Column
	@Size(min = 1, message = "Hotel must have at least one room")
	private int numOfRooms;

	@Column(unique = true, nullable = false)
	private String address;

	@Column(nullable = false)
	private String postcode;

	@Column(nullable = false)
	private String city;

	@Column(length = 8000)
	private String amenities;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "bookingId")
	private List<Bookings> bookings;

	@Column
	private int starRating;

	@ManyToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn(name = "roomId")
	private List<Room> room;

	@Column
	private boolean airportTransfers;

	@Column
	private int transferPrice;

	@Column
	private boolean verified;

	public Hotel() {
		super();
	}

	public Hotel(String hotelName, int numOfRooms, String address, String postcode, String city, String ammenities) {
		this.hotelName = hotelName;
		this.numOfRooms = numOfRooms;
		this.address = address;
		this.postcode = postcode;
		this.city = city;
		this.amenities = ammenities;
	}

	public Hotel(String hotelName, int numOfRooms, String address, String postcode, String city, String amenities,
				 List<Bookings> bookings, int starRating, List<Room> room, boolean airportTransfers, int transferPrice,
				 boolean verified) {
		super();
		this.hotelName = hotelName;
		this.numOfRooms = numOfRooms;
		this.address = address;
		this.postcode = postcode;
		this.city = city;
		this.amenities = amenities;
		this.bookings = bookings;
		this.starRating = starRating;
		this.room = room;
		this.airportTransfers = airportTransfers;
		this.transferPrice = transferPrice;
		this.verified = verified;
	}

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public int getNumOfRooms() {
		return numOfRooms;
	}

	public void setNumOfRooms(int numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAmenities() {
		return amenities;
	}

	public void setAmenities(String ammenities) {
		this.amenities = ammenities;
	}

	public List<Bookings> getBookings() {
		return bookings;
	}

	public void setBookings(List<Bookings> bookings) {
		this.bookings = bookings;
	}

	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}

	public List<Room> getRoom() {
		return room;
	}

	public void setRoom(List<Room> room) {
		this.room = room;
	}

	public boolean isAirportTransfers() {
		return airportTransfers;
	}

	public void setAirportTransfers(boolean airportTransfers) {
		this.airportTransfers = airportTransfers;
	}

	public int getTransferPrice() {
		return transferPrice;
	}

	public void setTransferPrice(int transferPrice) {
		this.transferPrice = transferPrice;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (airportTransfers ? 1231 : 1237);
		result = prime * result + ((amenities == null) ? 0 : amenities.hashCode());
		result = prime * result + ((bookings == null) ? 0 : bookings.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + (int) (hotelId ^ (hotelId >>> 32));
		result = prime * result + ((hotelName == null) ? 0 : hotelName.hashCode());
		result = prime * result + numOfRooms;
		result = prime * result + ((postcode == null) ? 0 : postcode.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + starRating;
		result = prime * result + transferPrice;
		result = prime * result + (verified ? 1231 : 1237);
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
		Hotel other = (Hotel) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (airportTransfers != other.airportTransfers)
			return false;
		if (amenities == null) {
			if (other.amenities != null)
				return false;
		} else if (!amenities.equals(other.amenities))
			return false;
		if (bookings == null) {
			if (other.bookings != null)
				return false;
		} else if (!bookings.equals(other.bookings))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (hotelId != other.hotelId)
			return false;
		if (hotelName == null) {
			if (other.hotelName != null)
				return false;
		} else if (!hotelName.equals(other.hotelName))
			return false;
		if (numOfRooms != other.numOfRooms)
			return false;
		if (postcode == null) {
			if (other.postcode != null)
				return false;
		} else if (!postcode.equals(other.postcode))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (starRating != other.starRating)
			return false;
		if (transferPrice != other.transferPrice)
			return false;
		if (verified != other.verified)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", hotelName=" + hotelName + ", numOfRooms=" + numOfRooms + ", address="
				+ address + ", postcode=" + postcode + ", city=" + city + ", amenities=" + amenities + ", bookings="
				+ bookings + ", starRating=" + starRating + ", room=" + room + ", airportTransfers=" + airportTransfers
				+ ", transferPrice=" + transferPrice + ", verified=" + verified + "]";
	}

}