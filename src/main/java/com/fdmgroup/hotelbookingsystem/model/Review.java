package com.fdmgroup.hotelbookingsystem.model;


import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;


@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_gen")
    @SequenceGenerator(name = "review_gen",sequenceName = "REVIEW_SEQ", allocationSize = 1)
    private long reviewId;

    @OneToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;


    @OneToOne
    @JoinColumn(name = "customerId")
    private User customer;

    @Column
    @NotNull
    private String message;

    @Column
    @NotNull
    @Min(value = 0)
    @Max(value = 5)
    private int score;

    public Review() {
    }

    public Review(Hotel hotel, User customer, String message, int score) {
        this.hotel = hotel;
        this.customer = customer;
        this.message = message;
        this.score = score;
    }


    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reviewId == review.reviewId &&
                score == review.score &&
                hotel.equals(review.hotel) &&
                customer.equals(review.customer) &&
                message.equals(review.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, hotel, customer, message, score);
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId=" + reviewId +
                ", hotel=" + hotel +
                ", customer=" + customer +
                ", message='" + message + '\'' +
                ", score=" + score +
                '}';
    }
}
