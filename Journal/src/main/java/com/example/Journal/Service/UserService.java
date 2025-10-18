package com.example.Journal.Service;

import com.example.Journal.Entity.JournalEntry;
import com.example.Journal.Entity.User;
import com.example.Journal.Repository.JournalEntryRepository;
import com.example.Journal.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryRepository journalEntryRepository;


    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // using this method just to avoid password change
    public void saveUser(User user){
        try {
            user.setRoles(List.of("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error Occurred userName-{}: ",user.getUserName() ,e);
        }
    }

    // using this method where password is change
    public void saveNewUser(User user){
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error Occurred userName-{}: ",user.getUserName() ,e);
            throw new RuntimeException(e);
        }
    }

    // returns all the users
    public List<User> getAll(){
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error Occurred to  get user: ", e);
        }
        return Collections.emptyList();
    }

    // finds user by id
    public Optional<User> findById(ObjectId id){
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            log.error("Error Occurred to get user by id: ", e);
        }
        return Optional.empty();
    }

    // finds user by username
    public User findByUserName(String userName){
        try {
            return userRepository.findByUserName(userName);
        } catch (Exception e) {
            log.error("Error Occurred to get user by name: ", e);
        }
        return null;
    }

    // used for deleting users
    @Transactional
    public void deleteUserByUserName(String userName) {

        try {
            User user = userRepository.findByUserName(userName);

            if(user == null){
                throw new IllegalArgumentException("User not found username: " + userName);
            }

            final List<JournalEntry> journalEntries = user.getJournalEntries();

            if(journalEntries != null && !journalEntries.isEmpty()){
                // remove entries from the list
                journalEntryRepository.deleteAll(journalEntries);
            }

            // remove the user
            userRepository.deleteByUserName(userName);
        } catch (IllegalArgumentException e) {
            log.error("Error Occurred unable to delete: ", e);
        }
    }

    // creating new admin
    public void saveNewAdmin(User admin) {
        try {
            admin.setPassword(encoder.encode(admin.getPassword()));
            admin.setRoles(Arrays.asList("USER", "ADMIN"));
            userRepository.save(admin);
        } catch (Exception e) {
            log.error("Error Occurred unable to save new admin: ", e);

        }
    }
}
