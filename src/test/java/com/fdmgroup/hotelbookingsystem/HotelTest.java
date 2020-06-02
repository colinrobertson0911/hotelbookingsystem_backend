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

	final static String HOTEL_ROOT_URI = "/hotelbookingsystem/hotel";

	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@Test
	public void listOfHotelsInCityExists() throws Exception {
		 this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByCity/Edinburgh")
				.session(session))
				.andExpect(status().isOk());
	}
	
	@Test
	public void listOfHotelsInCityDoesNotExists() throws Exception {
		 this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByCity/London")
				.session(session))
				.andExpect(status().isNotFound());
	}

	@Test
	public void listOfHotelsWithRoomType() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByRoomType/STANDARD")
				.session(session))
				.andExpect(status().isOk());
	}

	@Test
	public void listOfHotelsWithInvalidRoomType() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByRoomType/WEDDING")
				.session(session))
				.andExpect(status().isNotFound());
	}

	@Test
	public  void listOfHotelsWithAvailabilityisShown() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByAvailability/2020/04/05,2020/04/12")
		.session(session))
				.andExpect(status().isOk());
	}

	@Test
	public  void listOfHotelsWithNoAvailabilityisShown() throws Exception {
		this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SearchByAvailability/2020/04/20,2020/04/20")
				.session(session))
				.andExpect(status().isNotFound());
	}

	@Test
	public void seeAHotelThatExists() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTEL_ROOT_URI + "/SeeHotel/2")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":50,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"ammenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}
	
	@Test
	public void addABooking() throws Exception {
		Bookings booking = new Bookings("STANDARD", "Travelodge Glasgow", LocalDate.of(2020, 04, 20), LocalDate.of(2020, 04, 27), new BigDecimal("60.00"), new BigDecimal("0.00"), new BigDecimal("420.00"), Extras.NO_EXTRAS);
		this.mockMvc.perform(post(HOTEL_ROOT_URI + "/BookingSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(booking)))
				.andExpect(status().isOk());
		
	}
	
}
