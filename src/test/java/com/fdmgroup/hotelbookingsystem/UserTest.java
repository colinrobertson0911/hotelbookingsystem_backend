package com.fdmgroup.hotelbookingsystem;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

	private AuthenticationRequest signupDto = new AuthenticationRequest("harry", "1234", "Harry", "Wilson");
	private User user = new User(signupDto.getUsername(), signupDto.getPassword(), signupDto.getFirstName(), signupDto.getLastName(), new Role());

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private UserSecurityService service;

	@Test
	public void signin(){
		restTemplate.postForEntity("/login/LoginUser", new AuthenticationRequest("admin", "myPass"), Void.class);
		verify(this.service).signin("admin", "myPass");
	}

	@Test
	public void registerUser(){
		when(service.signup(signupDto.getUsername(), signupDto.getPassword(), signupDto.getFirstName(), signupDto.getLastName())).thenReturn(Optional.of(user));

		ResponseEntity<User> responseEntity = restTemplate.exchange("/login/RegisterUser", POST,
				new HttpEntity<>(signupDto),
				User.class);

		assertThat(responseEntity.getStatusCode().value(), is(201));
		assertThat(responseEntity.getBody().getUsername(), is(user.getUsername()));
		assertThat(responseEntity.getBody().getFirstName(), is(user.getFirstName()));
		assertThat(responseEntity.getBody().getLastName(), is(user.getLastName()));
		assertThat(responseEntity.getBody().getRoles().size(), is(user.getRoles().size()));
	}

	@Test
	public void registerInvalidUser() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		when(service.signup(authenticationRequest.getUsername(), authenticationRequest.getPassword(), authenticationRequest.getFirstName(), authenticationRequest.getLastName())).thenReturn(Optional.of(user));
		ResponseEntity<User> responseEntity = restTemplate.exchange("/login/RegisterUser", POST,
				new HttpEntity<>(authenticationRequest),
				User.class);

		assertThat(responseEntity.getStatusCode().value(), is(400));

	}

}
