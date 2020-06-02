package com.fdmgroup.hotelbookingsystem;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

}
