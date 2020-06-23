package com.fdmgroup.hotelbookingsystem.repository;


import com.fdmgroup.hotelbookingsystem.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review, Long> {

    @Query(value = "SELECT * FROM REVIEW WHERE REVIEW.HOTELID = ?", nativeQuery = true)
    List<Review> findAllByHotelId(long hotelId);

}
