package com.fdmgroup.hotelbookingsystem.utility;

import com.fdmgroup.hotelbookingsystem.model.Review;

import java.util.List;

public class AverageHotelRating {

    public static double getAverageFromListOfReviews(List<Review> reviewList){
        double total = 0;
        if(reviewList == null){
            return 0;
        }
        for (Review review: reviewList){
            total += review.getScore();
        }
        return total / reviewList.size();
    }
}
