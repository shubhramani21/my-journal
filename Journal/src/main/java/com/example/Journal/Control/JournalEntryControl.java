package com.example.Journal.Control;

import com.example.Journal.Entity.JournalEntry;
import com.example.Journal.Entity.User;
import com.example.Journal.Service.JournalEntryService;
import com.example.Journal.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControl {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    // 1. get all entries
    @GetMapping
    public ResponseEntity<?> getAll() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userName = authentication.getName();

        final List<JournalEntry> all = userService.findByUserName(userName).getJournalEntries();
        if (all.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(all, HttpStatus.OK);

    }

    // 2. create entry
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry journalEntry) {
        try {
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final String userName = authentication.getName();

            journalEntryService.saveEntry(journalEntry, userName);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // 3. get entry by id
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable ObjectId id) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        final User user = userService.findByUserName(userName);

        final List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).toList();

        if (!collect.isEmpty()) {
            final Optional<JournalEntry> entryOptional = journalEntryService.findById(id);
            if (entryOptional.isPresent()) {
                return new ResponseEntity<JournalEntry>(entryOptional.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    // 4. update entry
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateEntryById(
            @RequestBody JournalEntry newEntry,
            @PathVariable ObjectId id
    ) {

        SecurityContextHolder.getContext().getAuthentication();


        final JournalEntry oldEntry = journalEntryService.findById(id).orElse(null);
        if (oldEntry != null) {
            // Corrected content check: update only if provided and non-blank
            String newContent = (newEntry.getContent() != null && !newEntry.getContent().trim().isEmpty())
                    ? newEntry.getContent().trim()  // Optional: trim to clean up whitespace
                    : oldEntry.getContent();

            String newTitle = newEntry.getTitle() != null && !newEntry.getTitle().trim().isEmpty()
                    ? newEntry.getTitle().trim()    // Optional: trim to clean up whitespace
                    : oldEntry.getTitle();

            oldEntry.setContent(newContent);
            oldEntry.setTitle(newTitle);

            journalEntryService.saveEntry(oldEntry);  // Assuming this handles persistence correctly

            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // 5. delete entry
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId id) {

        // checking authentication
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String userName = authentication.getName();


        // if entry is not present
        if (journalEntryService.findById(id).isEmpty() || userService.findByUserName(userName) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        journalEntryService.deleteEntryById(id, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
