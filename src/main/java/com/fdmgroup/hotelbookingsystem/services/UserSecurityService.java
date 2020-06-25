package com.fdmgroup.hotelbookingsystem.services;

import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.model.User;
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
	
    private AuthenticationManager authenticationManager;
    
    private RoleDao roleDao;
    
    private PasswordEncoder passwordEncoder;
    
    private JwtProvider jwtProvider;
    
    @Autowired
    public UserSecurityService(UserDao userDao, AuthenticationManager authenticationManager,
                               PasswordEncoder passwordEncoder, RoleDao roleDao, JwtProvider jwtProvider) {
    	this.userDao = userDao;
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
	public Optional<User> signup(String username, String password, String firstName, String lastName){
		LOGGER.info("New user attempting to sign in");
		Optional<User> user = Optional.empty();
		if (!userDao.findByUsername(username).isPresent()) {
			Optional<Role> role = roleDao.findByRoleName("ROLE_CUSTOMER");
			user = Optional.of(userDao.save(new User(username,
					passwordEncoder.encode(password),
					firstName,
					lastName,
					role.get())));
		}
		return user;
	}

	public Optional<User> addHotelOwner(String username, String password, String firstName, String lastName){
		LOGGER.info("New user attempting to sign in");
		Optional<User> user = Optional.empty();
		if (!userDao.findByUsername(username).isPresent()) {
			Optional<Role> role = roleDao.findByRoleName("ROLE_HOTELOWNER");
			user = Optional.of(userDao.save(new User(username,
					passwordEncoder.encode(password),
					firstName,
					lastName,
					role.get())));
		}
		return user;
	}

}
