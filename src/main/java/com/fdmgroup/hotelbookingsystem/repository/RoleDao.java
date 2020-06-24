package com.fdmgroup.hotelbookingsystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.hotelbookingsystem.model.Role;

public interface RoleDao extends JpaRepository<Role, Long>{

	Optional<Role> findByRoleName(String name);
	
	List<Role> findAll();
}
