package com.projectlib.librarian.controller;

import com.projectlib.librarian.jwt.JwtResponse;
import com.projectlib.librarian.jwt.JwtTokenUtil;
import com.projectlib.librarian.model.User_table;
import com.projectlib.librarian.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


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
    public ResponseEntity<String> registerUser(@RequestBody User_table user) {
        authService.createUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User_table user) {
        User_table foundUser = authService.findByUsername(user.getUsername());

        if (foundUser == null || !passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String role = foundUser.getRole();
        String token = jwtTokenUtil.generateToken(user.getUsername(), role);

        JwtResponse jwtResponse = new JwtResponse(token);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }
}
