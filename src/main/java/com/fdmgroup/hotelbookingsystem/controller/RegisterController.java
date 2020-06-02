package com.fdmgroup.hotelbookingsystem.controller;

import com.fdmgroup.hotelbookingsystem.model.User;
import com.fdmgroup.hotelbookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:4200")
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping("/RegisterUserSubmit")
    public ResponseEntity<HttpStatus> registerUserSubmit(@RequestBody User user){
        try {
            userService.save(user);
        }catch(DataIntegrityViolationException e) {
            return new ResponseEntity<HttpStatus> (HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(HttpStatus.CREATED);

    }

}

