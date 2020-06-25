package com.fdmgroup.hotelbookingsystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer extends User {


    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "customer_booking", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "bookingId"))
    private List<Booking> bookings;

    public Customer() {
        super();
    }

    public Customer(String username, String password, String firstName, String lastName, String address, String email, Role role) {
        super(username, password, firstName, lastName, address, email, role);
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getBookings(), customer.getBookings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getBookings());
    }

    @Override
    public String toString() {
        return "Customer{" +
                "bookings=" + bookings +
                '}';
    }
}
