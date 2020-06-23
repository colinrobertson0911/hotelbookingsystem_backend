package com.fdmgroup.hotelbookingsystem.services;


import com.fdmgroup.hotelbookingsystem.model.Review;
import com.fdmgroup.hotelbookingsystem.repository.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;

    public Review save(Review review) { return reviewDao.save(review); }

    public List<Review> findAll() { return reviewDao.findAll(); }

    public Optional<Review> findByReviewId(long reviewId) { return reviewDao.findById(reviewId); }
}
