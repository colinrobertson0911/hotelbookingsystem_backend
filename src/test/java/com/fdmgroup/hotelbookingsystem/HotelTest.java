package com.fdmgroup.hotelbookingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
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
class HotelTest {

	@Autowired
	HotelService hotelService;

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Test
	void test_thatANewHotelCanBeAdded() {
		Hotel hotel = new Hotel();
		hotel.setHotelName("Hotel1");
		hotel.setCity("Place");
		hotel.setNumOfRooms(10);
		hotel.setStarRating(5);
		hotel.setAirportTransfers(true);
		int numberBeforeAdding = hotelService.findAll().size();
		hotelService.save(hotel);
		int numberAfterAdding = hotelService.findAll().size();
		assertNotEquals(numberBeforeAdding, numberAfterAdding);
	}

	@Test
	void test_thatAHotelCanBeCalledById() {
		Hotel hotel = hotelService.retrieveOne(1L);
		long hotelId = hotel.getHotelId();
		Hotel hotelFromDatabase = hotelService.retrieveOne(hotelId);
		assertEquals(hotel.getHotelId(), hotelFromDatabase.getHotelId());

	}

	@Test
	void test_thatAListOfHotelsFromACertainCityCanBeCalled() {
		List<Hotel> hotelsByCity = hotelService.findByCity("Glasgow");
		assert (hotelsByCity.size() > 1);
	}

	@Test
	void test_thatTheNumberOfRoomsIsCalledFromAHotel() {
		Hotel hotel = hotelService.retrieveOne(1L);
		int roomNumber = hotel.getNumOfRooms();
		assertEquals(roomNumber, 5);
	}

	@Test
	public void test_ThatTheManyToManyJoinWorks() {
		HotelOwner hotelOwner = hotelOwnerService.retrieveOne(1L);
		List<Hotel> hotel = hotelService.findAll();
		hotelOwner.getHotel();
		int numberOfHotels = hotel.size();
		assert (numberOfHotels > 0);
	}

	@Test
	public void test_ThatHotelsCanBeFoundSearchedByRoomType() {
		List<Hotel> hotel = hotelService.findByRoomType("STANDARD");
		int listSizeFromHotel = hotel.size();
		assertTrue(listSizeFromHotel > 0);
	}

	@Test
	public void test_RetrieveAllVerifiedHotels() {
		List<Hotel> hotel = hotelService.findByVerifiedEqualsTrue();
		int listSizeFromHotel = hotel.size();
		assert (listSizeFromHotel > 0);
	}

	@Test
	public void test_RetrieveByCityAndVerifiedIsTrue() {
		List<Hotel> hotel = hotelService.findByCityAndVerifiedIsTrue("Glasgow");
		assert (hotel.size() > 0);
	}

	@Test
	public void test_ThatVerifiedHotelsCanBeFoundByRoomType() {
		List<Hotel> hotel = hotelService.findByVerifiedAndRoomType("STANDARD");
		int listSizeFromHotel = hotel.size();
		assert (listSizeFromHotel > 0);
	}

	@Test
	public void test_ToSeeIfListOfAvailableHotelsIsReturned() {
		List<Hotel> hotels = hotelService.findByAvailability();
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test
	public void test_ToSeeIfListOfAvailableAndVerifiedHotelsIsReturned() {
		List<Hotel> hotels = hotelService.findByAvailabilityAndVerified();
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test
	public void test_ToSeeIfListOfAvailableCurrentlyAndVerifiedHotelsIsReturned() {
		List<Hotel> hotels = hotelService.findByAvailabilityAndVerifiedWithCurrentDate();
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test
	public void test_ToSeeIfHotelCanBeRetrievedByAddress() {
		Optional<Hotel> hotels = hotelService.findByAddress("1 main street");
		assertEquals("1 main street", hotels.get().getAddress());
	}

	@Test
	/**
	 * Dates from importSQL 2020/04/05, 2020/04/12
	 * 
	 */
	public void test_SeeAvailabilityOfHotelsDuringSearchedDates() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 8);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 12);
		List<Hotel> hotels = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test

	public void test_SeeAvailabilityOfHotelsDuringSearchedDates2() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 01);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 8);
		List<Hotel> hotels = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test
	public void test_SeeAvailabilityOfHotelsDuringSearchedDates3() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 8);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 14);
		List<Hotel> hotels = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test
	public void test_SeeAvailabilityOfHotelsDuringSearchedDates4() {
		LocalDate checkInDate = LocalDate.of(2020, 04, 20);
		LocalDate checkOutDate = LocalDate.of(2020, 04, 27);
		List<Hotel> hotels = hotelService.findByAvailabilityAndVerifiedWithSpecifiedDates(checkInDate, checkOutDate);
		int hotelSize = hotels.size();
		assert (hotelSize > 0);
	}

	@Test
	public void test_FindHotelByName() {
		Hotel hotel = hotelService.findByHotelName("Travelodge Glasgow");
		Hotel hotelById = hotelService.retrieveOne(1L);
		long hotelId = hotel.getHotelId();
		assertEquals(hotelId, hotelById.getHotelId());

	}

}
