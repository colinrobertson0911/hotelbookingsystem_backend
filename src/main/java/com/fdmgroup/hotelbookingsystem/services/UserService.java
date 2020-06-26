package com.fdmgroup.hotelbookingsystem.services;

import com.fdmgroup.hotelbookingsystem.model.*;
import com.fdmgroup.hotelbookingsystem.repository.CustomerDao;
import com.fdmgroup.hotelbookingsystem.repository.HotelOwnerDao;
import com.fdmgroup.hotelbookingsystem.repository.ReviewDao;
import com.fdmgroup.hotelbookingsystem.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	ReviewDao reviewDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	HotelOwnerDao hotelOwnerDao;

	@Autowired
	CustomerService customerService;

	@Autowired
	HotelOwnerService hotelOwnerService;

	@Autowired
	ReviewService reviewService;

	public Page<User> findAll(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);
		return userDao.findAll(pageRequest);
	}

	public User save(User user) {
		return userDao.save(user);
	}

	public Optional<User> findById(long userId){
		return userDao.findById(userId);
	}

	public Optional<User> findByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
	public List<User> findAllHotelOwners(int page, int size){
		Page<User> users = findAll(page, size);
		List<User> owners = new ArrayList<>();
		for (User user : users){
			if (user.getRoles().equals("HOTELOWNER")){
				owners.add(user);
			}
		}
		return owners;
	}

	public User updateUser(long userId, String username, String firstName, String lastName, List<Role> role) throws NoSuchElementException {
		User user = userDao.findById(userId).get();
		if (username != null){
			user.setUsername(username);
		}
		if (firstName != null){
			user.setFirstName(firstName);
		}
		if (lastName != null) {
			user.setLastName(lastName);
		}
		return userDao.save(user);
	}


	public User updateRole(User user, List<Role> role) throws NoSuchElementException {
		Customer customer = new Customer();
		HotelOwner hotelOwner = new HotelOwner();

		if (role.get(0).getRoleName().equals("ROLE_CUSTOMER")){

			customer.setUsername(user.getUsername());
			customer.setFirstName(user.getFirstName());
			customer.setLastName(user.getLastName());
			customer.setAddress(user.getAddress());
			customer.setEmail(user.getEmail());
			customer.setRoles(user.getRoles());
			userDao.save(customer);

			HotelOwner hotelOwner2 = hotelOwnerService.findByUsername(user.getUsername()).get();

			hotelOwnerDao.delete(hotelOwner);

			customer.setUserId(customer.getUserId());
			customerService.save(customer);

			return userDao.save(customer);
		}
		else if(role.get(0).getRoleName().equals("ROLE_HOTELOWNER")){
			List<Review> reviews= reviewService.findAllCustomerReviews(user.getUserId());
			for (Review review:reviews) {
				reviewDao.delete(review);
			}

			Customer customer2 = customerService.findByUsername(user.getUsername()).get();
			customerDao.delete(customer2);

			hotelOwner.setUsername(user.getUsername());
			hotelOwner.setFirstName(user.getFirstName());
			hotelOwner.setLastName(user.getLastName());
			hotelOwner.setAddress(user.getAddress());
			hotelOwner.setEmail(user.getEmail());
			hotelOwner.setRoles(user.getRoles());
			userDao.save(hotelOwner);

			hotelOwner.setUserId(hotelOwner.getUserId());
			hotelOwnerService.save(hotelOwner);
			return userDao.save(hotelOwner);
		}

		return null;

	}

}
