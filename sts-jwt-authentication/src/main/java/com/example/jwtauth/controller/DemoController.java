package com.example.jwtauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {
    @GetMapping("/profile")
    public String profile(Authentication authentication) {
        return "Hello " + authentication.getName() + "! You accessed a protected endpoint using JWT.";
    }
}
