package com.fdmgroup.hotelbookingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class HotelOwnerTest {

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	HotelService hotelService;

	@Test
	void test_ThatANewOwnerCanBeAdded() {
		List<Hotel> hotels = hotelService.findAll();
		HotelOwner hotelOwner = new HotelOwner();
		hotelOwner.setUsername("user4");
		hotelOwner.setEmail("Use21@email.com");
		hotelOwner.setName("user two");
		hotelOwner.setPassword("password");
		hotelOwner.setHotel(hotels);
		int numberBeforeAdding = hotelOwnerService.findAll().size();
		hotelOwnerService.save(hotelOwner);
		int numberAfterAdding = hotelOwnerService.findAll().size();
		assertNotEquals(numberBeforeAdding, numberAfterAdding);
	}

	@Test
	void test_RetrieveAListOfOwners() {
		List<HotelOwner> hotelOwners = hotelOwnerService.findAll();
		int numOfOwners = hotelOwners.size();
		assert (numOfOwners > 0);
	}

	@Test
	void test_RetrieveAnOwnerById() {
		HotelOwner hotelOwner = hotelOwnerService.retrieveOne(1L);
		long hotelOwnerId = hotelOwner.getHotelOwnerId();
		HotelOwner hotelOwnerFromDB = hotelOwnerService.retrieveOne(hotelOwnerId);
		assertEquals(hotelOwnerFromDB.getEmail(), hotelOwner.getEmail());
	}

	@Test
	void test_RetreieveByUserEmail() {
		HotelOwner hotelOwner = hotelOwnerService.findByEmail("user1@email.com");
		assertEquals("user1@email.com", hotelOwner.getEmail());
	}

	@Test
	void test_RetrieveByUsernameAndPassword() {
		Optional<HotelOwner> hotelOwner = hotelOwnerService.findByUsernameAndPassword("user1", "password");
		assertEquals("user one", hotelOwner.get().getName());
	}

	@Test
	void test_ThatHotelOwnerCanBeRetreivedByUsername() {
		Optional<HotelOwner> hotelOwner = hotelOwnerService.findByUsername("user1");
		assertEquals("user1", hotelOwner.get().getUsername());
	}

}
