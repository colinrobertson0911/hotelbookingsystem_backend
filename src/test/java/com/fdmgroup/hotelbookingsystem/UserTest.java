package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Extras;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.hotelbookingsystem.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper objectMapper;
	
	MockMvc mockMvc;
	
	MockHttpSession session;
	
	final static String LOGIN_ROOT_URI = "/login";
	final static String REGISTER_ROOT_URI = "/register";

	@BeforeEach
	public void setUp() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SharedHttpSessionConfigurer.sharedHttpSession())
				.build();
	}
	
	@Test
	public void userLoginExists() throws Exception {
		this.mockMvc.perform(get(LOGIN_ROOT_URI + "/LoginUserSubmit/1"))
				.andExpect(status().isOk());
	}

	@Test
	public  void newAdminUserCanRegister() throws Exception{
		User newUser = new User("user100", "password", "user100@email.com", UserType.ADMIN);
		this.mockMvc.perform(post(REGISTER_ROOT_URI + "/RegisterUserSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk());
	}

	@Test
	public  void newCustomerUserCanRegister() throws Exception{
		User newUser = new User("user101", "password", "user101@email.com", UserType.CUSTOMER);
		this.mockMvc.perform(post(REGISTER_ROOT_URI + "/RegisterUserSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk());
	}

	@Test
	public  void newHotelOwnerUserCanRegister() throws Exception{
		User newUser = new User("user102", "password", "user102@email.com", UserType.HOTELOWNER);
		this.mockMvc.perform(post(REGISTER_ROOT_URI + "/RegisterUserSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isOk());
	}

	@Test
	public  void invalidNewUserCantRegister() throws Exception{
		User newUser = new User();
		this.mockMvc.perform(post(REGISTER_ROOT_URI + "/RegisterUserSubmit")
				.session(session)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(newUser)))
				.andExpect(status().isConflict());
	}

}
