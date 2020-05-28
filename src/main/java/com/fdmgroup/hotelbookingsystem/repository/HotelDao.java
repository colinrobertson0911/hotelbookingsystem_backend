package com.fdmgroup.hotelbookingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fdmgroup.hotelbookingsystem.model.Hotel;

public interface HotelDao extends JpaRepository<Hotel, Long> {

	Hotel findByHotelId(long hotelId);

	List<Hotel> findByCity(String city);

	Optional<Hotel> findByAddress(String address);

	@Query(value = "SELECT * FROM HOTEL INNER JOIN HOTEL_ROOM ON HOTEL.HOTELID = HOTEL_ROOM.HOTEL_HOTELID INNER JOIN ROOM ON HOTEL_ROOM.ROOM_ROOMID = ROOM.ROOMID WHERE ROOM.ROOMTYPE = ?", nativeQuery = true)
	List<Hotel> findByRoomType(String roomType);

	List<Hotel> findByVerifiedIsTrue();

	List<Hotel> findByCityAndVerifiedIsTrue(String city);

	@Query(value = "SELECT * FROM HOTEL INNER JOIN HOTEL_ROOM ON HOTEL.HOTELID = HOTEL_ROOM.HOTEL_HOTELID INNER JOIN ROOM ON HOTEL_ROOM.ROOM_ROOMID = ROOM.ROOMID WHERE ROOM.ROOMTYPE = ? AND HOTEL.VERIFIED=true", nativeQuery = true)
	List<Hotel> findByVerifiedAndRoomType(String roomType);

	Hotel findByHotelName(String hotelName);

}
