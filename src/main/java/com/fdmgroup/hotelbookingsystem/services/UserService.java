package com.fdmgroup.hotelbookingsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.repository.UserDao;

@Service
public class UserService implements GeneralServiceRepository<User> {

	@Autowired
	UserDao userDao;

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return userDao.findByUsernameAndPassword(username, password);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public Optional<User> retrieveOne(long userId) {
		return userDao.findById(userId);
	}

	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
