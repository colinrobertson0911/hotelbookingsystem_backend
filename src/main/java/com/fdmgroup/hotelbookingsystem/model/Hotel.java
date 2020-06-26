package com.fdmgroup.hotelbookingsystem.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fdmgroup.hotelbookingsystem.utility.AverageHotelRating;
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
	@Min(value = 1, message = "Hotel must have at least one room")
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
	@JoinTable(name = "hotel_booking", joinColumns =
	@JoinColumn(name = "hotelId"), inverseJoinColumns =
	@JoinColumn(name = "bookingId"))
	private List<Booking> bookings;

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

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "hotelId")
	List<Review> reviews;

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
                 List<Booking> bookings, int starRating, List<Room> room, boolean airportTransfers, int transferPrice,
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

	public double getAverageHotelScore(){
		return AverageHotelRating.getAverageFromListOfReviews(this.reviews);
	}

	public long getHotelId() {
		return hotelId;
	}

	public void setHotelId(long hotelId) {
		this.hotelId = hotelId;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
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

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Hotel hotel = (Hotel) o;
		return hotelId == hotel.hotelId &&
				numOfRooms == hotel.numOfRooms &&
				starRating == hotel.starRating &&
				airportTransfers == hotel.airportTransfers &&
				transferPrice == hotel.transferPrice &&
				verified == hotel.verified &&
				Objects.equals(hotelName, hotel.hotelName) &&
				Objects.equals(address, hotel.address) &&
				Objects.equals(postcode, hotel.postcode) &&
				Objects.equals(city, hotel.city) &&
				Objects.equals(amenities, hotel.amenities) &&
				Objects.equals(bookings, hotel.bookings) &&
				Objects.equals(room, hotel.room);
	}

	@Override
	public int hashCode() {
		return Objects.hash(hotelId, hotelName, numOfRooms, address, postcode, city, amenities, bookings, starRating, room, airportTransfers, transferPrice, verified);
	}

	@Override
	public String toString() {
		return "Hotel{" +
				"hotelId=" + hotelId +
				", hotelName='" + hotelName + '\'' +
				", numOfRooms=" + numOfRooms +
				", address='" + address + '\'' +
				", postcode='" + postcode + '\'' +
				", city='" + city + '\'' +
				", amenities='" + amenities + '\'' +
				", bookings=" + bookings +
				", starRating=" + starRating +
				", room=" + room +
				", airportTransfers=" + airportTransfers +
				", transferPrice=" + transferPrice +
				", verified=" + verified +
				'}';
	}
}
