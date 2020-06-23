package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.services.BookingService;
import com.fdmgroup.hotelbookingsystem.services.HotelService;

@SpringBootTest
@AutoConfigureMockMvc
class HotelTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	HotelService hotelService;
	
	@Autowired
	BookingService bookingService;

	MockMvc mockMvc;
	
	MockHttpSession session;

	final static String HOTEL_ROOT_URI = "/hotel";

	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@Test
	public void listOfHotelsInCityExists() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByCity/Edinburgh")
				.param("page", "0").param("size", "2")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "[{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":1,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"amenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}]";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}
	
	@Test
	public void listOfHotelsInCityDoesNotExists() throws Exception {
		 this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByCity/London")
				.param("page", "0").param("size", "2")
				.session(session))
				.andExpect(status().isNoContent());
	}

	@Test
	public void listOfHotelsWithRoomType() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByRoomType/LUXURY")
				.param("page", "0").param("size", "2")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "[{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":1,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"amenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}]";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}

	@Test
	public void listOfHotelsWithInvalidRoomType() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByRoomType/WEDDING")
				.param("page", "0").param("size", "2")
				.session(session))
				.andExpect(status().isNoContent());
	}

	@Test
	public  void listOfHotelsWithAvailabilityIsShown() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByAvailability/2020-12-05,2020-12-12")
				.param("page", "0").param("size", "2")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "[{\"hotelId\":1,\"hotelName\":\"Travelodge Glasgow\",\"numOfRooms\":2,\"address\":\"1 main street\",\"postcode\":\"g43 6pq\",\"city\":\"Glasgow\",\"amenities\":\"none\",\"bookings\":[{\"bookingId\":1,\"roomType\":\"STANDARD\",\"hotel\":\"Travelodge Glasgow\",\"checkInDate\":\"2020-07-23\",\"checkOutDate\":\"2020-07-27\",\"roomPrice\":60.00,\"extrasPrice\":20.00,\"totalPrice\":440.00,\"extras\":\"AIRPORTTRANSFER\"},{\"bookingId\":2,\"roomType\":\"STANDARD\",\"hotel\":\"Travelodge Glasgow\",\"checkInDate\":\"2020-07-15\",\"checkOutDate\":\"2020-07-25\",\"roomPrice\":60.00,\"extrasPrice\":20.00,\"totalPrice\":440.00,\"extras\":\"AIRPORTTRANSFER\"}],\"starRating\":3,\"room\":[{\"roomId\":1,\"roomType\":\"STANDARD\",\"price\":60.00,\"roomTypeAndPrice\":\"STANDARD 60.00\"},{\"roomId\":4,\"roomType\":\"SUITE\",\"price\":120.00,\"roomTypeAndPrice\":\"SUITE 120.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true},{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":1,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"amenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}]";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}

	@Test
	public  void listOfHotelsWithNoAvailabilityIsShown() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByAvailability/2020-07-20,2020-07-25")
				.session(session))
				.andExpect(status().isNoContent());
	}

	@Test
	public void seeAHotelThatExists() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SeeHotel/Yotel")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":1,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"amenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}

	@Test
	public void seeAHotelThatExistsById() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SeeHotelById/2")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":1,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"amenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}
	
	@Test
	@WithUserDetails("hotelOwner1")
	public void seeAllRooms() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/AllRooms"))
				.andExpect(status().isOk());
	}

	
}
