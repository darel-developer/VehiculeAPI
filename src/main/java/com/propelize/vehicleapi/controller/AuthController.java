package com.propelize.vehicleapi.controller;

import com.propelize.vehicleapi.dto.AuthRequest;
import com.propelize.vehicleapi.dto.AuthResponse;
import com.propelize.vehicleapi.model.User;
import com.propelize.vehicleapi.service.UserService;
import com.propelize.vehicleapi.service.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint d'inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        try {
            User user = new User(authRequest.getName(), authRequest.getPassword());
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body("Ce nom d'utilisateur existe déjà.");
        }
    }

    // Endpoint de connexion
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = userService.authenticate(authRequest);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }
}