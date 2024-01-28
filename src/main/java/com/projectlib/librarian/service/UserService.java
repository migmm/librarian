package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.UserDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.User_table;
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

    public List<User_table> getAllUsers() {
        return userRepository.findAll();
    }

    public User_table getUserById(Long id) {

        return userRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("User with ID " + id + " does not exist."));
    }

    public String createUser(@Valid UserDTO userDTO) {
        User_table user = new User_table();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return "User created successfully.";
    }

    public String updateUser(Long id, UserDTO updatedUser) {
        User_table existingUser = getUserById(id);
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

    public String setStatus(Long id, Boolean userTable) {
        User_table existingUser = getUserById(id);
        if (existingUser == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        existingUser.setStatus(userTable);
        userRepository.save(existingUser);
        return "User status updated successfully.";
    }

    public String deleteUser(Long id) {
        User_table existingUser = getUserById(id);
        if (existingUser == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        userRepository.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }
}
