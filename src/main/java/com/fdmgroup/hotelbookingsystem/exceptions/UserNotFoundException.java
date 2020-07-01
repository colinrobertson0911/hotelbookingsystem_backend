package com.fdmgroup.hotelbookingsystem.exceptions;

public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1003L;

    public UserNotFoundException(String username) {
        super("Login failed for {} " + username);
    }
}
