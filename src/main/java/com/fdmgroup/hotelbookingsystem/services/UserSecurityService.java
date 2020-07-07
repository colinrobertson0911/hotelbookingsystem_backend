package com.fdmgroup.hotelbookingsystem.services;

import com.fdmgroup.hotelbookingsystem.exceptions.UserNotFoundException;
import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.repository.CustomerDao;
import com.fdmgroup.hotelbookingsystem.repository.HotelOwnerDao;
import com.fdmgroup.hotelbookingsystem.repository.RoleDao;
import com.fdmgroup.hotelbookingsystem.repository.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Service
public class UserSecurityService {
//
	private static final Logger LOGGER = LoggerFactory.getLogger(UserSecurityService.class);

	private UserDao userDao;

	private CustomerDao customerDao;

	private HotelOwnerDao hotelOwnerDao;

    private RoleDao roleDao;

    @Autowired
    public UserSecurityService(UserDao userDao, CustomerDao customerDao, HotelOwnerDao hotelOwnerDao, RoleDao roleDao) {
    	this.userDao = userDao;
    	this.customerDao = customerDao;
    	this.hotelOwnerDao = hotelOwnerDao;
    	this.roleDao = roleDao;
    }

	/**
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @param username  username
     * @param password  password
     * @return Optional of the Java Web Token, empty otherwise
     */
	public Optional<User> signin(String username, String password) {
		LOGGER.info("New user attempting to sign in");
		Optional<User> user = userDao.findByUsername(username);
		if (user.isPresent()) {
			return user;
		} else {
			LOGGER.info("Log in failed for user {}", username);
		}
		return null;
	}

	/**
     * Create a new user in the database.
     *
     * @param username username
     * @param password password
     * @param firstName first name
     * @param lastName last name
     * @return Optional of user, empty if the user already exists.
     */
	public Optional<Customer> signup(String username, String password, String firstName, String lastName, String address, String email){
		LOGGER.info("New user attempting to sign in");
		Optional<Customer> customer = Optional.empty();
		if (!userDao.findByUsername(username).isPresent()) {
			Optional<Role> role = roleDao.findByRoleName("ROLE_CUSTOMER");
			customer = Optional.of(customerDao.save(new Customer(username,
					password,
					firstName,
					lastName,
					address,
					email,
					role.get())));
		}
		return customer;
	}

}
