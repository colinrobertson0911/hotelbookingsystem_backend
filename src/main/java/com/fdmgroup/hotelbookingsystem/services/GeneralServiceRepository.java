package com.fdmgroup.hotelbookingsystem.services;

import java.util.List;

public interface GeneralServiceRepository<E> {

	E findByUsernameAndPassword(String username, String password);

	E findByUsername(String username);

}
