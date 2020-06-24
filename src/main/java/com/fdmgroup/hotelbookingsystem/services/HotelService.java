package com.fdmgroup.hotelbookingsystem.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.Booking;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.repository.HotelDao;

@Service
public class HotelService {

	@Autowired
	HotelDao hotelDao;

	public Page<Hotel> findAll(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);
		return hotelDao.findAll(pageRequest);
	}

	public Optional<Hotel> findByAddress(String address) {
		return hotelDao.findByAddress(address);
	}

	public Hotel save(Hotel hotel) {
		return hotelDao.save(hotel);
	}

	public Optional<Hotel> retrieveOne(String hotelName) {
		return hotelDao.findByHotelName(hotelName);
	}
	
	public Optional<Hotel> findById( long hotelId) {
		return hotelDao.findById(hotelId);
	}

	public List<Hotel> findByCity(String city, int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);
		return hotelDao.findByCity(city, pageRequest);
	}

	public List<Hotel> findByRoomType(String roomType, int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);
		return hotelDao.findByRoomType(roomType, pageRequest);
	}


	public List<Hotel> findByVerifiedEqualsTrue() {
		return hotelDao.findByVerifiedIsTrue();
	}
	

	public List<Hotel> findByAvailabilityWithSpecifiedDates(LocalDate startDate, LocalDate endDate) {
		List<Hotel> availableHotelsByDate = new ArrayList<Hotel>();
		List<Booking> bookingInDateWindow = new ArrayList<Booking>();
		List<Hotel> hotels = findByVerifiedEqualsTrue();
		for (Hotel hotel : hotels) {
			List<Booking> hotelBookings = hotel.getBookings();
			for (Booking booking : hotelBookings) {
				if (!booking.getCheckInDate().isAfter(endDate) && !booking.getCheckOutDate().isBefore(startDate)) {
					bookingInDateWindow.add(booking);
				}
			}
			if (hotel.getNumOfRooms() > bookingInDateWindow.size()) {
				availableHotelsByDate.add(hotel);
			}
		}
		return availableHotelsByDate;
	}

	public Hotel findByHotelId(long hotelId) { return hotelDao.findByHotelId(hotelId); }
}
