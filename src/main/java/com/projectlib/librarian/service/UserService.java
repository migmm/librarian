package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.UserDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.UserModel;
import com.projectlib.librarian.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User with ID " + id + " does not exist."));
    }

    public String createUser(@Valid UserDTO userDTO) {
        UserModel user = new UserModel();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return "User created successfully.";
    }

    public String updateUser(Long id, UserDTO updatedUser) {
        UserModel existingUser = getUserById(id);
        if (existingUser == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        existingUser.setName(updatedUser.getName());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setStatus(updatedUser.getStatus());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setRole(updatedUser.getRole());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPassword(passwordEncoder.encode(existingUser.getPassword()));

        userRepository.save(existingUser);
        return "User updated successfully.";
    }

    public String setStatus(Long id, Boolean status) {
        UserModel existingUser = getUserById(id);
        if (existingUser == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        existingUser.setStatus(status);
        userRepository.save(existingUser);
        return "User status updated successfully.";
    }

    public String deleteUser(Long id) {
        UserModel existingUser = getUserById(id);
        if (existingUser == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        userRepository.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }
}
