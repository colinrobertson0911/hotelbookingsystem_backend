package com.fdmgroup.hotelbookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithUserDetails("admin1")
class AdminControllerTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	UserService userService;
	
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
	@WithUserDetails("admin1")
	public void getAllHotelOwners() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/AllOwners")
				.param("page", "0").param("size", "2"))
		.andExpect(status().isOk());
	}

	@Test
	@WithUserDetails("hotelOwner1")
	public void singleHotelOwnerOneExists() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/SeeHotelOwner/hotelOwner2")
				.session(session))
				.andExpect(status().isOk());
		
	}

	@Test
	@WithUserDetails("hotelOwner1")
	public void singleHotelOwnerDoesNotExists() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/SeeHotelOwner/hotelOwner3")
				.session(session))
				.andExpect(status().isConflict());

	}
	
	@Test
	public void addHotelOwner() throws Exception {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest("user5", "password", "Simon", "Wilson");
		this.mockMvc.perform(post(ADMIN_ROOT_URI + "/addHotelOwner")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(authenticationRequest)))
				.andExpect(status().isCreated());
	}

	@Test
	public void addInvalidHotelOwner() throws Exception {
		User user = new User();
		this.mockMvc.perform(post(ADMIN_ROOT_URI + "/addHotelOwner")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest());
	}
	

	
	@Test
	public void editUser() throws Exception {
		User user = userService.findById(2L).get();
		user.setUsername("user99");
		ResultActions mvcResult = this.mockMvc.perform(patch(ADMIN_ROOT_URI + "/EditUser")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
		String expectedResult = "{\"userId\":2,\"username\":\"user99\",\"firstName\":\"Tom\",\"lastName\":\"Smith\",\"roles\":[{\"roleId\":2,\"roleName\":\"ROLE_HOTELOWNER\",\"authority\":\"ROLE_HOTELOWNER\"},{\"roleId\":4,\"roleName\":\"ROLE_VIEWER\",\"authority\":\"ROLE_VIEWER\"}],\"hotels\":[{\"hotelId\":1,\"hotelName\":\"Travelodge Glasgow\",\"numOfRooms\":2,\"address\":\"1 main street\",\"postcode\":\"g43 6pq\",\"city\":\"Glasgow\",\"amenities\":\"none\",\"bookings\":[{\"bookingId\":1,\"roomType\":\"STANDARD\",\"hotel\":\"Travelodge Glasgow\",\"checkInDate\":\"2020-07-23\",\"checkOutDate\":\"2020-07-27\",\"roomPrice\":60.00,\"extrasPrice\":20.00,\"totalPrice\":440.00,\"extras\":\"AIRPORTTRANSFER\"},{\"bookingId\":2,\"roomType\":\"STANDARD\",\"hotel\":\"Travelodge Glasgow\",\"checkInDate\":\"2020-07-15\",\"checkOutDate\":\"2020-07-25\",\"roomPrice\":60.00,\"extrasPrice\":20.00,\"totalPrice\":440.00,\"extras\":\"AIRPORTTRANSFER\"}],\"starRating\":3,\"room\":[{\"roomId\":1,\"roomType\":\"STANDARD\",\"price\":60.00,\"roomTypeAndPrice\":\"STANDARD 60.00\"},{\"roomId\":4,\"roomType\":\"SUITE\",\"price\":120.00,\"roomTypeAndPrice\":\"SUITE 120.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true},{\"hotelId\":2,\"hotelName\":\"Yotel\",\"numOfRooms\":1,\"address\":\"some street\",\"postcode\":\"EH71 7FA\",\"city\":\"Edinburgh\",\"amenities\":\"bowling alley\",\"bookings\":[],\"starRating\":4,\"room\":[{\"roomId\":2,\"roomType\":\"LUXURY\",\"price\":80.00,\"roomTypeAndPrice\":\"LUXURY 80.00\"},{\"roomId\":3,\"roomType\":\"DELUXE\",\"price\":100.00,\"roomTypeAndPrice\":\"DELUXE 100.00\"}],\"airportTransfers\":true,\"transferPrice\":20,\"verified\":true}]}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString()); 
		
	}

	@Test
	public void getListOfHotels() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/AllHotels")
				.param("page", "0").param("size", "2"))
		.andExpect(status().isOk());
				
	}

	@Test
	public void getListOfAllUsers() throws Exception {
		this.mockMvc.perform(get(ADMIN_ROOT_URI + "/AllUsers")
				.param("page", "0").param("size", "2"))
				.andExpect(status().isOk());
	}

}
