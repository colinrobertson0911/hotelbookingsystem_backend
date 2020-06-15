package com.fdmgroup.hotelbookingsystem.services;

import com.fdmgroup.hotelbookingsystem.model.Customer;
import com.fdmgroup.hotelbookingsystem.repository.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerDao customerDao;

    public Optional<Customer> findByUsernameAndPassword(String username, String password) {
        return customerDao.findByUsernameAndPassword(username, password);
    }

    public Optional<Customer> findByUsername(String username) {
        return customerDao.findByUsername(username);
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public Customer save(Customer customer){
        return customerDao.save(customer);
    }
}
