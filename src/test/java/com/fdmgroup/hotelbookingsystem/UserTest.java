package com.fdmgroup.hotelbookingsystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserService;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserTest {

	@Autowired
	UserService userService;

	@Test
	public void whenKnownUserFindByUsernameAndPasswordThenUserExists() {
		User user = userService.findByUsernameAndPassword("admin1", "password");
		assertEquals(userService.findByUsernameAndPassword("admin1", "password").getUserId(), user.getUserId());
	}

	@Test
	public void whenKnownUserFindByUsernameThenUserExists() {
		User user = userService.findByUsername("admin1");
		assertEquals(userService.findByUsername("admin1").getUserId(), user.getUserId());
	}

	@Test
	void testThatRetrieveAllRetrievesListOfUsers() {
		List<User> users = userService.findAll();
		assertFalse(users.isEmpty());
	}

	@Test
	void testThatRetrieveByIdWorksReturnsTheCorrectUser() {
		User user = userService.retrieveOne(1).get();
		long userId = user.getUserId();
		User user2 = userService.retrieveOne(userId).get();
		assertEquals(user, user2);
	}

	@Test
	public void testThatNewUserCanBeCreated() {
		int numUsersBefore = userService.findAll().size();
		User user1 = new User();
		user1.setUsername("Pete");
		user1.setPassword("Pass");
		user1.setEmail("fjfjg@hgjh");
		userService.save(user1);
		int numUsersAfter = userService.findAll().size();
		assertNotEquals(numUsersBefore, numUsersAfter);
	}

}
