package com.fdmgroup.hotelbookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.hotelbookingsystem.model.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	User findByUsername(@Param("username") String username);
}
