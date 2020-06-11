package com.fdmgroup.hotelbookingsystem.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.repository.RoomDao;

@Service
public class RoomService {

	@Autowired
	RoomDao roomDao;

	public List<Room> findAll(){
		return roomDao.findAll();
	}

	public Room save(Room room){
		return roomDao.save(room);
	}
	public List<Room> findByRoomType(String roomType) {
		return roomDao.findByRoomType(roomType);
	}

	public List<Room> findByPrice(BigDecimal price) {

		return roomDao.findByPrice(price);
	}

	public Optional<Room> findByRoomId(Long roomId) {
		return roomDao.findByRoomId(roomId);
	}

	public Optional<Room> findByRoomTypeAndPrice(String roomType, BigDecimal price) {
		return roomDao.findByRoomTypeAndPrice(roomType, price);
	}

}
