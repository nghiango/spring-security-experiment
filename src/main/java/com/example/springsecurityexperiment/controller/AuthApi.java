package com.example.springsecurityexperiment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/public")
public class AuthApi {
    // Autowire AuthenticationManager
    // Autowire JwtTokenUtil
    // Autowire userViewMapper
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody String username, @RequestBody String password) {
        // https://www.toptal.com/spring/spring-security-tutorial
        return ResponseEntity.ok("Haiz");
    }
}
