package com.example.Journal.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.Journal.Service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SpringSecurity{

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/public/**", "/login", "/signup", "/css/**", "/js/**").permitAll()
                                .requestMatchers("/journal/**", "/user/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")           // Your custom login page
                        .loginProcessingUrl("/login")  // Where Spring Security processes login
                        .defaultSuccessUrl("/entries", true)  // Redirect after successful login
                        .failureUrl("/login?error=true")   // Redirect on failure
                        .permitAll())
                        
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                 .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)  // For development only
                .build();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){ // more like config
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        return provider;

    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
