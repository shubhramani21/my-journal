package com.example.Journal.Control;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Journal.Entity.User;
import com.example.Journal.Service.UserService;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicControl {

    @Autowired
    private UserService userService;


    // sign Up
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        try {
            userService.saveNewUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
