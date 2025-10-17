package com.example.Journal.Service;

import com.example.Journal.Entity.JournalEntry;
import com.example.Journal.Entity.User;
import com.example.Journal.Repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;



    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            final User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            final JournalEntry saved = journalEntryRepository.save(journalEntry);

            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("Error Occurred: TITLE-{}", journalEntry.getTitle(), e);
        }

    }


    public void saveEntry(JournalEntry journalEntry){
        try {
            journalEntryRepository.save(journalEntry);
        }catch (Exception e){
            log.error("Error Occurred: TITLE-{}", journalEntry.getTitle(), e);
        }

    }

    public Optional<JournalEntry> findById(ObjectId id){
        try {
            return journalEntryRepository.findById(id);
        } catch (Exception e) {
            log.error("Error Occurred: ", e);
        }
        return Optional.empty();
    }

    @Transactional
    public void deleteEntryById(ObjectId id, String userName){
        try {
            final User user = userService.findByUserName(userName);
            final boolean removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        } catch (Exception e) {
            log.error("Error Occurred: ", e);
        }


    }


}
