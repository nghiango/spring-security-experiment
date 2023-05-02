package com.example.springsecurityexperiment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jwt")
public class JwtAuthController {

    @GetMapping("user")
    public String helloUser() {
        return "in JWT User";
    }
    @GetMapping("admin")
    public String helloAdmin() {
        return "in JWT Admin";
    }
}
