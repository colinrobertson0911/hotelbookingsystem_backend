package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;

@SpringBootTest
@AutoConfigureMockMvc
class HotelOwnerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	MockMvc mockMvc;

	MockHttpSession session;
	
	final static String HOTELOWNER_ROOT_URI = "/hotelbookingsystem/admin";
//	final static String PUBLISHER_ONE_JSON = "{\"hotelOwnerId\":1,\"username\":\"user1\",\"password\":\"password\",\"email\":\"user1@email.com\",\"name\":\"user one\"}";
	
	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@Test
	public void getAllHotelOwners() throws Exception {
		this.mockMvc.perform(get(HOTELOWNER_ROOT_URI + "/AllOwners"))
		.andExpect(status().isOk());
	}

	@Test
	public void singleHotelOwnerOneExists() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTELOWNER_ROOT_URI + "/SeeHotelOwner/3")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelOwnerId\":3,\"username\":\"user3\","
				+ "\"password\":\"password\",\"email\":\"user3@email.com\",\"name\":\"Wee Dan\",\"hotel\":[]}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
		
	}
	
	@Test
	public void addHotelOwnerWhenOwnerIsValid() throws Exception {
		HotelOwner hotelOwner = new HotelOwner("user5", "password", "user5@email.com", "Big Dan", new ArrayList<Hotel>());
		this.mockMvc.perform(post(HOTELOWNER_ROOT_URI + "/addHotelOwner")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotelOwner)))
				.andExpect(status().isOk());
	}
	
	
	@Test
	public void getListOfHotels() throws Exception {
		this.mockMvc.perform(get(HOTELOWNER_ROOT_URI + "/AllHotels"))
		.andExpect(status().isOk());
				
	}
	
	@Test
	public void singleHotelOneExists() throws Exception {
		ResultActions mvcResult = this.mockMvc.perform(get(HOTELOWNER_ROOT_URI + "/VerifyHotel/1")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelId\":1,\"hotelName\":\"Travelodge Glasgow\","
				+ "\"numOfRooms\":5,\"address\":\"1 main street\",\"postcode\":\"g43 6pq\",\"city\":\"Glasgow\",\"ammenities\":\"none\",\"bookings\":[],\"starRating\":3,\"room\":[],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
		
	}
}
