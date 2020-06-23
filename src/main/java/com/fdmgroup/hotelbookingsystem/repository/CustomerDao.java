package com.fdmgroup.hotelbookingsystem.repository;

import com.fdmgroup.hotelbookingsystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDao extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);

}
