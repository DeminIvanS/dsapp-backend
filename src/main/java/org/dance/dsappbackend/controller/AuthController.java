package org.dance.dsappbackend.controller;

import org.dance.dsappbackend.dto.AuthResponse;
import org.dance.dsappbackend.dto.LoginRequest;
import org.dance.dsappbackend.dto.RefreshRequest;
import org.dance.dsappbackend.dto.RegisterRequest;
import org.dance.dsappbackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createUser(@RequestBody RegisterRequest request) {
        String message = authService.createUser(request);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/login")

    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/refresh")

    public AuthResponse refresh(@RequestBody RefreshRequest request) {
        return authService.refresh(request);
    }
}
