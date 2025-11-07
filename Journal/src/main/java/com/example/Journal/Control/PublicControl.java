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
            
            String userName = user.getUserName();

            // checking if user already exsits based on username
            if (userService.findByUserName(userName) != null) {
                log.warn("User already exists {}", user.getUserName());
                return new ResponseEntity<>("Username already exists. Please choose another.", HttpStatus.CONFLICT);
            }

            // checking if email is used for any other account
            if (userService.findByEmail(user.getEmail()) != null) {
                log.warn("Email already exists {}", user.getEmail());
                return new ResponseEntity<>("An account with this email already exists.", HttpStatus.CONFLICT);
            }

            userService.saveNewUser(user);

            log.info("New User created successfully {}", user.getUserName());

            return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);

        } catch (Exception e) {

            log.error("Failed to create user: {}", e.getMessage(), e);

            return new ResponseEntity<>("Error creating account. Please try again.", HttpStatus.BAD_REQUEST);
        }
    }

}
