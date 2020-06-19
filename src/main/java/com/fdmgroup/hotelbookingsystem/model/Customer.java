package com.fdmgroup.hotelbookingsystem.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
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

    public Customer() {
        super();
    }

    public Customer(String username, String password, String firstName, String lastName, Role role, String address,
                    String email, List<Bookings> bookings) {
        super(username, password, firstName, lastName, role);
        this.address = address;
        this.email = email;
        this.bookings = bookings;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Customer))
            return false;
        if (!super.equals(o))
            return false;
        Customer customer = (Customer) o;
        return getAddress().equals(customer.getAddress()) && getEmail().equals(customer.getEmail())
                && Objects.equals(getBookings(), customer.getBookings());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getAddress(), getEmail(), getBookings());
    }

    @Override
    public String toString() {
        return "Customer{" + "address='" + address + '\'' + ", email='" + email + '\'' + ", bookings=" + bookings + '}';
    }
}
