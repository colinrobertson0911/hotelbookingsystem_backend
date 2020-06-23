package com.fdmgroup.hotelbookingsystem.repository;

import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelOwnerDao extends JpaRepository<HotelOwner, Long> {


	Optional<HotelOwner> findByUsername(String username);
	

}
