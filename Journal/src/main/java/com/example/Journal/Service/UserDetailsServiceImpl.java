package com.example.Journal.Service;

import com.example.Journal.Entity.User;
import com.example.Journal.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        System.out.println("🔍 Attempting login for user: " + username);
        System.out.println("✅ Found user: " + user.getUserName());
        System.out.println("✅ Roles: " + user.getRoles());

        return new CustomUserDetails(user);
    }

}
