package com.fdmgroup.hotelbookingsystem.repository;


import com.fdmgroup.hotelbookingsystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewDao extends JpaRepository<Review, Long> {

}
