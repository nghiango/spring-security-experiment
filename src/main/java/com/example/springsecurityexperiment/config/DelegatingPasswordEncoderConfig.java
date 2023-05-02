package com.example.springsecurityexperiment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
//https://docs.spring.io/spring-security/reference/features/authentication/password-storage.html#authentication-password-storage-dpe
public class DelegatingPasswordEncoderConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

//    public static void main(String[] args) {
//        System.out.println(new BcryptPasswordEncoder().passwordEncoder().encode("Pass1234"));
//    }
}
