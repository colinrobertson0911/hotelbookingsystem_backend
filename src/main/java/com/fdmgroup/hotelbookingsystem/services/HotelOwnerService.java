package com.fdmgroup.hotelbookingsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.repository.HotelOwnerDao;

@Service
public class HotelOwnerService {

	@Autowired
	HotelOwnerDao hotelOwnerDao;

	public Optional<HotelOwner> findByUsername(String username) {
		return hotelOwnerDao.findByUsername(username);
	}

	public List<HotelOwner> findAll() {
		return hotelOwnerDao.findAll();
	}

	public HotelOwner save(HotelOwner hotelOwner) {
		return hotelOwnerDao.save(hotelOwner);
	}
}
