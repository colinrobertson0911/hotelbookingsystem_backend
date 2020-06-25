package com.fdmgroup.hotelbookingsystem.services;

import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.repository.CustomerDao;
import com.fdmgroup.hotelbookingsystem.repository.HotelOwnerDao;
import com.fdmgroup.hotelbookingsystem.repository.RoleDao;
import com.fdmgroup.hotelbookingsystem.repository.UserDao;
import com.fdmgroup.hotelbookingsystem.security.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserSecurityService.class);
	
	private UserDao userDao;

	private CustomerDao customerDao;

	private HotelOwnerDao hotelOwnerDao;
	
    private AuthenticationManager authenticationManager;
    
    private RoleDao roleDao;
    
    private PasswordEncoder passwordEncoder;
    
    private JwtProvider jwtProvider;
    
    @Autowired
    public UserSecurityService(UserDao userDao, CustomerDao customerDao, HotelOwnerDao hotelOwnerDao, AuthenticationManager authenticationManager,
                               PasswordEncoder passwordEncoder, RoleDao roleDao, JwtProvider jwtProvider) {
    	this.userDao = userDao;
    	this.customerDao = customerDao;
    	this.hotelOwnerDao = hotelOwnerDao;
    	this.authenticationManager = authenticationManager;
    	this.roleDao = roleDao;
    	this.passwordEncoder = passwordEncoder;
    	this.jwtProvider = jwtProvider;
    }

	/**
     * Sign in a user into the application, with JWT-enabled authentication
     *
     * @param username  username
     * @param password  password
     * @return Optional of the Java Web Token, empty otherwise
     */
	public Optional<String> signin(String username, String password){
		LOGGER.info("New user attempting to sign in");
		Optional<String> token = Optional.empty();
		Optional<User> user = userDao.findByUsername(username);
		if (user.isPresent()) {
			try {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
			}catch (AuthenticationException e) {
				LOGGER.info("Log in failed for user {}", username);
			}
		}
		return token;
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
					passwordEncoder.encode(password),
					firstName,
					lastName,
					address,
					email,
					role.get())));
		}
		return customer;
	}

	public Optional<HotelOwner> addHotelOwner(String username, String password, String firstName, String lastName, String address, String email){
		LOGGER.info("New user attempting to sign in");
		Optional<HotelOwner> hotelOwner = Optional.empty();
		if (!userDao.findByUsername(username).isPresent()) {
			Optional<Role> role = roleDao.findByRoleName("ROLE_HOTELOWNER");
			hotelOwner = Optional.of(hotelOwnerDao.save(new HotelOwner(username,
					passwordEncoder.encode(password),
					firstName,
					lastName,
					address,
					email,
					role.get())));
		}
		return hotelOwner;
	}

}
