package com.fdmgroup.hotelbookingsystem;

import com.fdmgroup.hotelbookingsystem.model.Role;
import com.fdmgroup.hotelbookingsystem.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class JwtRequestHelper {

    @Autowired
    JwtProvider jwtProvider;

    /**
     * Generate the appropriate headers for JWT Authentication.
     *
     * @param roleName role identifier
     * @return Http Headers for Content-Type and Authorization
     */
    public HttpHeaders withRole(String roleName){
        HttpHeaders headers = new HttpHeaders();
        Role role = new Role();
        role.setRoleName(roleName);
        String token = jwtProvider.createToken("anonymous", Arrays.asList(role));
        headers.setContentType(APPLICATION_JSON);
        headers.add("Authorization", "bearer " + token);
        return headers;
    }
}
