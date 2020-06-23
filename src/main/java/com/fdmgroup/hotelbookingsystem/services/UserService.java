package com.fdmgroup.hotelbookingsystem.services;

import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public Page<User> findAll(int page, int size) {
		Pageable pageRequest = PageRequest.of(page, size);
		return userDao.findAll(pageRequest);
	}

	public User save(User user) {
		return userDao.save(user);
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

}
