package com.fdmgroup.hotelbookingsystem.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.hotelbookingsystem.model.Room;

public interface RoomDao extends JpaRepository<Room, Long> {

	List<Room> findByRoomType(String roomType, Pageable pageable);

	List<Room> findByPrice(BigDecimal price, Pageable pageable);

	Optional<Room> findByRoomId(Long roomId);

	Optional<Room> findByRoomTypeAndPrice(String roomType, BigDecimal price);

}
