package com.fdmgroup.hotelbookingsystem.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.repository.RoomDao;

@Service
public class RoomService {

	@Autowired
	RoomDao roomDao;

	public Page<Room> findAll(Pageable pageable){
		return roomDao.findAll(pageable);
	}

	public Room save(Room room){
		return roomDao.save(room);
	}
	public List<Room> findByRoomType(String roomType, Pageable pageable) {
		return roomDao.findByRoomType(roomType, pageable);
	}

	public List<Room> findByPrice(BigDecimal price, Pageable pageable) {

		return roomDao.findByPrice(price, pageable);
	}

	public Optional<Room> findByRoomId(Long roomId) {
		return roomDao.findByRoomId(roomId);
	}

	public Optional<Room> findByRoomTypeAndPrice(String roomType, BigDecimal price) {
		return roomDao.findByRoomTypeAndPrice(roomType, price);
	}

}
