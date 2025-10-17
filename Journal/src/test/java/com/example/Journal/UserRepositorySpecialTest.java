package com.example.Journal;

import com.example.Journal.Entity.User;
import com.example.Journal.Repository.UserRepositorySpecial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserRepositorySpecialTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private UserRepositorySpecial userRepositorySpecial;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(null, "testUser", "testPass", "test@example.com", true, new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testGetUserByEmailSuccess() {
        List<User> expectedUsers = Collections.singletonList(testUser);
        when(mongoTemplate.find(any(Query.class), eq(User.class))).thenReturn(expectedUsers);

        List<User> actualUsers = userRepositorySpecial.getUserByEmail();

        assertEquals(expectedUsers, actualUsers);
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(User.class));
    }

    @Test
    void testGetUserByEmailException() {
        when(mongoTemplate.find(any(Query.class), eq(User.class)))
                .thenThrow(new RuntimeException("Mongo Error"));

        List<User> actualUsers = userRepositorySpecial.getUserByEmail();

        assertEquals(Collections.emptyList(), actualUsers);
        verify(mongoTemplate, times(1)).find(any(Query.class), eq(User.class));
    }
}
