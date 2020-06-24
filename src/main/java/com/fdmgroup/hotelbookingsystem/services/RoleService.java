package com.fdmgroup.hotelbookingsystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.repository.RoleDao;

@Service
public class RoleService {
	
	@Autowired
	RoleDao roleDao;
	
	public List<Role> findAll() {
		return roleDao.findAll();
		
	}

}
