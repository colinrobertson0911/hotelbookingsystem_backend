package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import com.fdmgroup.hotelbookingsystem.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	HotelOwnerService hotelOwnerService;
	
	MockMvc mockMvc;

	MockHttpSession session;
	
	final static String ADMIN_ROOT_URI = "/admin";
	
	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}

	@AfterEach
	public void test() {
		this.session = null;
		this.mockMvc = null;
	}
	
	@Test
	public void getAllHotelOwners() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/AllOwners"))
		.andExpect(status().isOk());
	}

	@Test
	public void singleHotelOwnerOneExists() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/SeeHotelOwner/hotelOwner2")
				.session(session))
				.andExpect(status().isOk());
		
	}

	@Test
	public void singleHotelOwnerDoesNotExists() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/SeeHotelOwner/hotelOwner3")
				.session(session))
				.andExpect(status().isConflict());

	}
	
	@Test
	public void addHotelOwner() throws Exception {
		User user = new User("user5", "password", "Simon", "Wilson", "HOTELOWNER");
		this.mockMvc.perform(post(ADMIN_ROOT_URI + "/addHotelOwner")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	public void addInvalidHotelOwner() throws Exception {
		User user = new User();
		this.mockMvc.perform(post(ADMIN_ROOT_URI + "/addHotelOwner")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isConflict());
	}
	

	
	@Test
	public void editHotelOwner() throws Exception {
		HotelOwner hotelOwner = new HotelOwner("user6", "password");
		hotelOwner.setUsername("user99");
		ResultActions mvcResult = this.mockMvc.perform(put(ADMIN_ROOT_URI + "/EditHotelOwnerSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(hotelOwner)))
				.andExpect(status().isOk());
		String expectedResult = "{\"userId\":5,\"username\":\"user99\",\"password\":\"password\",\"firstName\":null,\"lastName\":null,\"role\":null}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString()); 
		
	}

	@Test
	public void getListOfHotels() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/AllHotels"))
		.andExpect(status().isOk());
				
	}

	@Test
	public void getListOfAllUsers() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/AllUsers"))
				.andExpect(status().isOk());
	}

}
