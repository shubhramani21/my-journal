package com.example.Journal.Entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Document(collection = "user")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    private ObjectId id;

    private String firstName;
    private String lastName;

    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    private String password;

    private String email;
    private boolean sentimentAnalysis;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles = new ArrayList<>();

}
