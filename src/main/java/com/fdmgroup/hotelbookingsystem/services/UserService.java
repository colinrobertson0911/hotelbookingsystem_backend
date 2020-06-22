package com.fdmgroup.hotelbookingsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.repository.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	public Page<User> findAll(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	public User save(User user) {
		return userDao.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
