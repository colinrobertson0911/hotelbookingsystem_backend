package com.fdmgroup.hotelbookingsystem.repository;

import java.util.List;
import java.util.Optional;

import com.fdmgroup.hotelbookingsystem.model.Bookings;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.hotelbookingsystem.model.Hotel;

public interface HotelDao extends JpaRepository<Hotel, Long> {

	Optional<Hotel> findByHotelId(long hotelId);

	List<Hotel> findByCity(String city, Pageable pageable);

	Optional<Hotel> findByAddress(String address);

	@Query(value = "SELECT * FROM HOTEL INNER JOIN HOTEL_ROOM ON HOTEL.HOTELID = HOTEL_ROOM.HOTEL_HOTELID INNER JOIN ROOM ON HOTEL_ROOM.ROOM_ROOMID = ROOM.ROOMID WHERE ROOM.ROOMTYPE = ?", nativeQuery = true)
	List<Hotel> findByRoomType(String roomType, Pageable pageable);

	@Query(value = "SELECT * FROM HOTEL INNER JOIN HOTEL_ROOM ON HOTEL.HOTELID = HOTEL_ROOM.HOTEL_HOTELID INNER JOIN ROOM ON HOTEL_ROOM.ROOM_ROOMID = ROOM.ROOMID WHERE ROOM.ROOMTYPE = ? AND HOTEL.VERIFIED=true", nativeQuery = true)
	List<Hotel> findByVerifiedAndRoomType(String roomType, Pageable pageable);

	Optional<Hotel> findByHotelName(String hotelName);

	@Query(value = "SELECT BOOKINGS FROM HOTEL WHERE HOTEL.HOTELNAME = ?", nativeQuery = true)
	List<Bookings> findAllBookings(String hotelName, Pageable pageable);
	
	List<Hotel> findByVerifiedIsTrue(Pageable pageable);
	
}
