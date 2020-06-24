package com.fdmgroup.hotelbookingsystem.services;


import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Review;
import com.fdmgroup.hotelbookingsystem.repository.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;

    public Review save(Review review) { return reviewDao.save(review); }

    public List<Review> findAll() { return reviewDao.findAll(); }

    public Optional<Review> findByReviewId(long reviewId) { return reviewDao.findById(reviewId); }

    public List<Review> findAllByHotelId(long hotelId){
        return reviewDao.findAllByHotelId(hotelId);
    }

    public Double getAverageScore(long hotelId) throws NoSuchElementException {
        List<Review> reviews = reviewDao.findAllByHotelId(hotelId);
        OptionalDouble average = reviews.stream().mapToInt((review) -> review.getScore()).average();
        return average.isPresent() ? average.getAsDouble():null;
    }
}
