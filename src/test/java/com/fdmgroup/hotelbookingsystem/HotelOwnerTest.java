package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;

@SpringBootTest
@AutoConfigureMockMvc
class HotelOwnerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	HotelOwnerService hotelOwnerServce;
	
	MockMvc mockMvc;

	MockHttpSession session;
	
final static String HOTELOWNER_ROOT_URI = "/hotelbookingsystem/hotelOwner";
	
	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@Test
	public void addHotelThatIsValid() throws Exception {
		Hotel hotel = new Hotel("Glasgow Hotel", 100, "Center of Glasgow", "G something", "Glasgow", "TV and bed", null, 4, null, false, 0, false);
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddHotelSubmit/1")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void addHotelThatIsInDatabase() throws Exception {
		Hotel hotel = new Hotel("Glasgow Hotel", 100, "Center of Glasgow", "G something", "Glasgow", "TV and bed", null, 4, null, false, 0, false);
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddHotelSubmit/1")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isImUsed());
	}

	@Test
	public void addHotelThatIsNotValid() throws Exception {
		Hotel hotel = new Hotel();
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/AddHotelSubmit/1")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isConflict());
	}

	@Test
	public void editHotel() throws Exception {
		Hotel hotel = new Hotel("Glasgow Hotel", 100, "Center of Glasgow", "G something", "Glasgow", "TV and bed", null, 4, null, false, 0, false);
		hotel.setHotelName("The awesome hotel");
		ResultActions mvcResult = this.mockMvc.perform(put(HOTELOWNER_ROOT_URI + "/EditHotelOwnerSubmit/1")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotel)))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelId\":5,\"hotelName\":\"The awesome hotel\","
				+ "\"numOfRooms\":100,\"address\":\"Center of Glasgow\",\"postcode\":\"G something\",\"city\":\"Glasgow\", \"ammenities\":\"Tv and bed\", \"bookings\":null \"starRating\":4, \"room\":null, \"airportTransfers\":false, \"transferPrice\":0, \"verified\":false}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());

	}
}
