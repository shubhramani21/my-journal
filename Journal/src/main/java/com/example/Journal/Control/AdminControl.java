package com.example.Journal.Control;

import com.example.Journal.Entity.User;
import com.example.Journal.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminControl {
    @Autowired
    private UserService userService;

    // 1. get all user
    @GetMapping("all-users")
    public ResponseEntity<?> getAll() {
        final List<User> all = userService.getAll();

        if (all != null || !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping("create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User admin) {
        try {
            if (admin != null) {
                userService.saveNewAdmin(admin);
                return new ResponseEntity<>(admin, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error: ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
