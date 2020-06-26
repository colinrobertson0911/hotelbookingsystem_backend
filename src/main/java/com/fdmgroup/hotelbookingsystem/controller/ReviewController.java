package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.Hotel;
import com.fdmgroup.hotelbookingsystem.model.Review;
import com.fdmgroup.hotelbookingsystem.services.HotelService;
import com.fdmgroup.hotelbookingsystem.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    ReviewService reviewService;
    @Autowired
    HotelService hotelService;

    @PostMapping("/createReview")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Review> createReview(@RequestBody Review review, @RequestParam("hotelId") long hotelId) {
        Optional<Hotel> hotel = hotelService.findById(hotelId);
        if (hotel.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            review.setHotel(hotel.get());
            reviewService.save(review);
        }catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PutMapping("/editReview/{reviewId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
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
