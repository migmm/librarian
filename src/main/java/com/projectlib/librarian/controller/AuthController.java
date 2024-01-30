package com.projectlib.librarian.controller;

import com.projectlib.librarian.jwt.JwtResponse;
import com.projectlib.librarian.jwt.JwtTokenUtil;
import com.projectlib.librarian.model.UserModel;
import com.projectlib.librarian.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;


@RestController
@RequestMapping("/auth")

public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthService userService, JwtTokenUtil jwtTokenUtil) {
        this.authService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/register")
    @Operation(summary = "Register User", description = "Register a new user.")
    public ResponseEntity<String> registerUser(@RequestBody UserModel user) {
        authService.createUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login an existing user.")
    public ResponseEntity<?> login(@RequestBody UserModel user) {
        UserModel foundUser = authService.findByUsername(user.getUsername());

        if (foundUser == null || !passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String role = foundUser.getRole();

        String accessToken = jwtTokenUtil.generateToken(user.getUsername(), role);
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getUsername(), role);

        JwtResponse jwtResponse = new JwtResponse(accessToken, refreshToken);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh Token", description = "Refresh access token and refresh token.")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> tokenMap) {
        String token = tokenMap.get("token");

        if (!jwtTokenUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        String username = jwtTokenUtil.getUsernameFromToken(token);
        String role = jwtTokenUtil.getRoleFromToken(token);

        String newAccessToken = jwtTokenUtil.generateToken(username, role);
        String newRefreshToken = jwtTokenUtil.generateRefreshToken(username, role);

        JwtResponse jwtResponse = new JwtResponse(newAccessToken, newRefreshToken);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Logout user.")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }
}