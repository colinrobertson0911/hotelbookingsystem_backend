package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

@SpringBootTest
@AutoConfigureMockMvc
class HotelOwnerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
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
		ResultActions mvcResult = this.mockMvc.perform(get(HOTELOWNER_ROOT_URI + "/SeeHotelOwner/1")
				.session(session))
				.andExpect(status().isOk());
		String expectedResult = "{\"hotelOwnerId\":1,\"username\":\"user1\","
				+ "\"password\":\"password\",\"email\":\"user1@email.com\",\"name\":\"user one\"}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
		
	}
	
	
	@Test
	public void getListOfHotels() throws Exception {
		this.mockMvc.perform(get(HOTELOWNER_ROOT_URI + "/AllHotels"))
		.andExpect(status().isOk());
		
				
				
	}

}
