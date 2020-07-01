package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.Review;
import com.fdmgroup.hotelbookingsystem.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/createReview")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try {
            reviewService.save(review);
        }catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PutMapping("/editReview/{reviewId}")
    public ResponseEntity<Review> editReview(@PathVariable("reviewId") Long reviewId){
        return ResponseEntity.ok(reviewService.findByReviewId(reviewId).get());
    }

    @GetMapping("/allReviews/{hotelId}")
    public ResponseEntity<List<Review>> allReviews(@PathVariable("hotelId") Long hotelId){
        return ResponseEntity.ok(reviewService.findAllByHotelId(hotelId));
    }

    @GetMapping("/averageReview/{hotelId}")
    public AbstractMap.SimpleEntry<String, Double> getAverage(@PathVariable("hotelId")Long hotelId){
        return new AbstractMap.SimpleEntry<>("average", reviewService.getAverageScore(hotelId));
    }
}
