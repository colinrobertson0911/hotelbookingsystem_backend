package com.fdmgroup.hotelbookingsystem;

import com.fdmgroup.hotelbookingsystem.model.AuthenticationRequest;
import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserSecurityService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpMethod.POST;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@BeforeEach
	public void createValidator() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@AfterEach
	public void close() {
		validatorFactory.close();
	}

	private AuthenticationRequest signupDto = new AuthenticationRequest("harry", "12345678", "Harry", "Wilson");
	private User user = new User(signupDto.getUsername(), signupDto.getPassword(), signupDto.getFirstName(), signupDto.getLastName(), new Role());

	@Autowired
	private TestRestTemplate restTemplate;

	@MockBean
	private UserSecurityService service;


	@Test
	public void signin(){
		restTemplate.postForEntity("/login/LoginUser", new AuthenticationRequest("admin", "myPass123"), Void.class);
		verify(this.service).signin("admin", "myPass123");
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
