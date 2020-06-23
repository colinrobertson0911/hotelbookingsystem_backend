package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.Bookings;
import com.fdmgroup.hotelbookingsystem.model.Review;
import com.fdmgroup.hotelbookingsystem.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @PostMapping("/createReview")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        try {
            reviewService.save(review);
        }catch (DataIntegrityViolationException e) {
            return new ResponseEntity<Review>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Review>(review, HttpStatus.CREATED);
    }
}
