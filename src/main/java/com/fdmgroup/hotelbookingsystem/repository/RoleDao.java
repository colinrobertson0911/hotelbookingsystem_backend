package com.fdmgroup.hotelbookingsystem.repository;

import com.fdmgroup.hotelbookingsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long>{

	Optional<Role> findByRoleName(String name);
}
