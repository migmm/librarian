package com.projectlib.librarian.service;

import com.projectlib.librarian.model.User_table;
import com.projectlib.librarian.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User_table findByUsername(String username) {

        return authRepository.findByUsername(username);
    }

    public String createUser(User_table user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authRepository.save(user);
        return "User created successfully.";
    }
}
