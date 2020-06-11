package com.fdmgroup.hotelbookingsystem;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomTest {

	@Autowired
	RoomService roomService;

	@Autowired
	HotelService hotelService;

	@Test
	public void test_ThatANewRoomCanBeAdded() {
		Room room = new Room();
		room.setRoomType("STANDARD");
		room.setPrice(new BigDecimal("70.00"));
		int numBeforeAdding = roomService.findAll().size();
		roomService.save(room);
		int numAfterAdding = roomService.findAll().size();
		assertNotEquals(numBeforeAdding, numAfterAdding);

	}

	@Test
	public void test_ThatAListOfRoomsCanBeRetrieved() {
		List<Room> allRooms = roomService.findAll();
		int numOfRooms = allRooms.size();
		assert (numOfRooms > 0);
	}

	@Test
	public void test_FindByRoomType() {
		List<Room> allRooms = roomService.findByRoomType("STANDARD");
		int numOfRooms = allRooms.size();
		assert (numOfRooms > 0);
	}

	@Test
	public void test_ThatRoomsCanBefoundByExactPrice() {
		List<Room> allRooms = roomService.findByPrice(new BigDecimal("120"));
		int numOfRooms = allRooms.size();
		assert (numOfRooms > 0);
	}

	@Test
	public void test_RoomCanBeObtainedByTypeAndPrice() {
		Room knownRoom = roomService.findByRoomId(1L).get();
		Optional<Room> room = roomService.findByRoomTypeAndPrice(knownRoom.getRoomType(), knownRoom.getPrice());
		assertTrue(room.isPresent());
	}

}
