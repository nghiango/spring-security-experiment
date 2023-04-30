package com.example.springsecurityexperiment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("public")
public class AuthController {
    // Autowire AuthenticationManager
    // Autowire JwtTokenUtil
    // Autowire userViewMapper
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        // https://www.toptal.com/spring/spring-security-tutorial
        return ResponseEntity.ok("Haiz");
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody String username, @RequestBody String password) {
        // https://www.toptal.com/spring/spring-security-tutorial
        return ResponseEntity.ok("Haiz");
    }
}
