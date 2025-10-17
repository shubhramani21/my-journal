package com.example.Journal.Control;

import com.example.Journal.Entity.User;
import com.example.Journal.Service.UserService;
import com.example.Journal.Service.WeatherService;
import com.example.Journal.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserControl {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;


    @GetMapping
    public ResponseEntity<?> greeting() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final WeatherResponse.Current response = weatherService.getWeather("Nagpur").getCurrent();

        final int feelsLike = response.getFeelsLike();
        final int temperature = response.getTemperature();
        final List<String> weatherDescriptions = response.getWeatherDescriptions();

        final String weatherDescString = String.join(", ", weatherDescriptions);

        String weatherInfo = "\nTemperature: " + temperature + "\nFeels Like: " + feelsLike + "\nDescription: " + weatherDescString;

        String msg = "Hi " + authentication.getName() + weatherInfo;
        return new ResponseEntity<>(msg, HttpStatus.OK);


    }

    // 1. update user details by userName
    @PutMapping
    public ResponseEntity<?> updateUserByUserName(@RequestBody User newUser) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userName = authentication.getName();


        if (newUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User oldUser = userService.findByUserName(userName);


        if (oldUser != null) {

            if (!newUser.getUserName().isEmpty() && !newUser.getUserName().equals(userName)) {
                oldUser.setUserName(newUser.getUserName());
            }

            String passWord = newUser.getPassword() != null && !newUser.getPassword().trim().isEmpty() ?
                    newUser.getPassword().trim() :
                    oldUser.getPassword().trim();

            oldUser.setPassword(passWord);

            userService.saveNewUser(oldUser);

            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // 2. get user by userName
    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        final User user = userService.findByUserName(userName);

        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //  3. delete user by id
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userName = authentication.getName();

        if (userName == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        // using repo only user is getting deleted
        userService.deleteUserByUserName(userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


}
