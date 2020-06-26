package com.fdmgroup.hotelbookingsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.repository.RoleDao;
import com.fdmgroup.hotelbookingsystem.services.CustomerService;
import com.fdmgroup.hotelbookingsystem.services.HotelOwnerService;
import com.fdmgroup.hotelbookingsystem.services.RoleService;
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

import java.util.Arrays;
import java.util.Optional;

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

	@Autowired
	CustomerService customerService;

	@Autowired
	RoleDao roleDao;

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
	public void editRoleHotelOwner() throws Exception {

		User user = userService.findById(2L).get();
		Role role = roleDao.findByRoleName("ROLE_CUSTOMER").get();
		user.setRoles(Arrays.asList(role));

		ResultActions mvcResult = this.mockMvc.perform(patch(ADMIN_ROOT_URI + "/EditRole")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
		String expectedResult = "{\"userId\":6,\"username\":\"hotelOwner1\",\"firstName\":\"Tom\",\"lastName\":\"Smith\",\"address\":\"1, nowhere, London\",\"email\":\"owner1@email.com\",\"roles\":[{\"roleId\":3,\"roleName\":\"ROLE_CUSTOMER\",\"authority\":\"ROLE_CUSTOMER\"}],\"bookings\":null}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
	}

	@Test
	public void editRoleCustomer() throws Exception {
		User user = userService.findById(4L).get();
		Role role = roleDao.findByRoleName("ROLE_HOTELOWNER").get();
		user.setRoles(Arrays.asList(role));

		ResultActions mvcResult = this.mockMvc.perform(patch(ADMIN_ROOT_URI + "/EditRole")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
		String expectedResult = "{\"userId\":6,\"username\":\"customer1\",\"firstName\":\"Harry\",\"lastName\":\"Wilson\",\"address\":\"1, somewhere, Glasgow, g24 0nt\",\"email\":\"harry@email.com\",\"roles\":[{\"roleId\":2,\"roleName\":\"ROLE_HOTELOWNER\",\"authority\":\"ROLE_HOTELOWNER\"}],\"hotels\":null}";
		Assertions.assertEquals(expectedResult, mvcResult.andReturn()
				.getResponse().getContentAsString());
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
		String expectedResult = "{\"userId\":2,\"username\":\"user99\",\"firstName\":\"Tom\",\"lastName\":\"Smith\",\"address\":\"1, nowhere, London\",\"email\":\"owner1@email.com\",\"roles\":[{\"roleId\":2,\"roleName\":\"ROLE_HOTELOWNER\",\"authority\":\"ROLE_HOTELOWNER\"}],";

		Assertions.assertTrue(mvcResult.andReturn()
				.getResponse().getContentAsString().contains(expectedResult));
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
