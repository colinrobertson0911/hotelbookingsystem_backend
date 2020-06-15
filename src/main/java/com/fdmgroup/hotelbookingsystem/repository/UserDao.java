package com.fdmgroup.hotelbookingsystem.repository;

import org.apache.catalina.LifecycleState;
import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fdmgroup.hotelbookingsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

	Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

	Optional<User> findByUsername(@Param("username") String username);

	Optional<User> findByUserId(long userId);

}
