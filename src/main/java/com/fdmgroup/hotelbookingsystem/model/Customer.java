package com.fdmgroup.hotelbookingsystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer extends User {

    @Column
    private String address;

    @Column
    private String email;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "customer_bookings", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "bookingId"))
    private List<Bookings> bookings;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "reviewId")
    private List<Review> reviews;

    public Customer() {
        super();
    }

    public Customer(String username, String password, String firstName, String lastName, Role role, String address, String email) {
        super(username, password, firstName, lastName, role);
        this.address = address;
        this.email = email;
        this.bookings = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Bookings> getBookings() {
        return bookings;
    }

    public void setBookings(List<Bookings> bookings) {
        this.bookings = bookings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(address, customer.address) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(bookings, customer.bookings) &&
                Objects.equals(reviews, customer.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, email, bookings, reviews);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", bookings=" + bookings +
                ", reviews=" + reviews +
                '}';
    }
}
