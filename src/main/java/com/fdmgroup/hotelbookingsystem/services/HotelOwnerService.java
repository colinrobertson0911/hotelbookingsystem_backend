package com.fdmgroup.hotelbookingsystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.HotelOwner;
import com.fdmgroup.hotelbookingsystem.repository.HotelOwnerDao;

@Service
public class HotelOwnerService implements GeneralServiceRepository {

	@Autowired
	HotelOwnerDao hotelOwnerDao;

	@Override
	public HotelOwner findByUsernameAndPassword(String username, String password) {
		return null;
	}

	@Override
	public HotelOwner findByUsername(String username) {
		return null;
	}

	public List<HotelOwner> findAll() {
		return hotelOwnerDao.findAll();
	}

	public HotelOwner save(HotelOwner hotelOwner) {
		return hotelOwnerDao.save(hotelOwner);
	}
}
