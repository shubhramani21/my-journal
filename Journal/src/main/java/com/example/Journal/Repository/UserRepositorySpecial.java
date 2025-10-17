package com.example.Journal.Repository;

import com.example.Journal.Entity.User;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserRepositorySpecial {
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public List<User> getUserByEmail(){
        try {
            Query query = new Query();

            query.addCriteria(Criteria.where("email").regex(EMAIL_REGEX));
            query.addCriteria(Criteria.where("sentimentAnalysis").is(true));

            return mongoTemplate.find(query, User.class);
        } catch (Exception e) {
            log.error("Mongo Error: ", e);
            return Collections.emptyList();
        }

    }
}
