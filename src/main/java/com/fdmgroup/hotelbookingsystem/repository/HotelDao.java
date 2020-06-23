package com.fdmgroup.hotelbookingsystem.repository;

import java.util.List;
import java.util.Optional;

import com.fdmgroup.hotelbookingsystem.model.Bookings;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.hotelbookingsystem.model.Hotel;

public interface HotelDao extends JpaRepository<Hotel, Long> {

	Page<Hotel> findAll(Pageable pageable);

	List<Hotel> findByCity(String city, Pageable pageable);

	Optional<Hotel> findByAddress(String address);

	@Query(value = "SELECT * FROM HOTEL INNER JOIN HOTEL_ROOM ON HOTEL.HOTELID = HOTEL_ROOM.HOTEL_HOTELID INNER JOIN ROOM ON HOTEL_ROOM.ROOM_ROOMID = ROOM.ROOMID WHERE ROOM.ROOMTYPE = ?", nativeQuery = true)
	List<Hotel> findByRoomType(String roomType, Pageable pageable);

	Optional<Hotel> findByHotelName(String hotelName);
	
	List<Hotel> findByVerifiedIsTrue();

	Hotel findByHotelId(long hotelId);
}
