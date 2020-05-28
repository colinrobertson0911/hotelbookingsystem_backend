package com.fdmgroup.hotelbookingsystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fdmgroup.hotelbookingsystem.model.HotelOwner;

public interface HotelOwnerDao extends JpaRepository<HotelOwner, Long> {

	Optional<HotelOwner> findByHotelOwnerId(Long hotelOwnerId);

	HotelOwner findByEmail(String email);

	Optional<HotelOwner> findByUsernameAndPassword(String username, String password);

	Optional<HotelOwner> findByUsername(String username);

}
