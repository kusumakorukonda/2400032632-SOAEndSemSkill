package com.example.jwtauth.service;

import com.example.jwtauth.dto.*;
import com.example.jwtauth.model.AppUser;
import com.example.jwtauth.repository.UserRepository;
import com.example.jwtauth.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository users, PasswordEncoder encoder, JwtService jwtService) {
        this.users = users; this.encoder = encoder; this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        if (users.existsByUsername(request.username()) || users.existsByEmail(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username or email already exists");
        AppUser user = new AppUser(request.username(), request.email(), encoder.encode(request.password()));
        users.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {
        AppUser user = users.findByUsername(request.username())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));
        if (!encoder.matches(request.password(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        return new AuthResponse(jwtService.generateToken(user), "Bearer", user.getUsername());
    }
}
