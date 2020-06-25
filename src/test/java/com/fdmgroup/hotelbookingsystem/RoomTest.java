package com.fdmgroup.hotelbookingsystem;

import com.fdmgroup.hotelbookingsystem.model.Room;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class RoomTest {

	@Autowired
	RoomService roomService;

	private static Validator validator;

	@BeforeEach
	public void createValidator() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

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
