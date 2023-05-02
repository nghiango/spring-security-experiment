package com.example.springsecurityexperiment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration()
public class InMemoryUserDetailsService {
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$/ny2hXHF9u61IgpGwflis.YEiWfYM7UcgApBmpxHO0DstcwH4SCIG")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$/ny2hXHF9u61IgpGwflis.YEiWfYM7UcgApBmpxHO0DstcwH4SCIG")
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
