package com.projectlib.librarian.service;

import com.projectlib.librarian.model.User_table;
import com.projectlib.librarian.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User_table> getAllUsers() {

        return userRepository.findAll();
    }

    public User_table getUserById(Long id) {

        return userRepository.findById(id).orElse(null);
    }

    public String createUser(User_table user) {
        userRepository.save(user);
        return "User created successfully.";
    }

    public String updateUser(Long id, User_table updatedUser) {
        User_table existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setName(updatedUser.getName());
            existingUser.setSurname(updatedUser.getSurname());
            existingUser.setStatus(updatedUser.getStatus());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser);
            return "User updated successfully.";
        }
        return "User does not exist.";
    }

    public String setStatus(Long id, Boolean newStatus) {
        User_table existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setStatus(newStatus);
            userRepository.save(existingUser);
            return "User status updated successfully.";
        }
        return "User does not exist.";
    }

    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }
}
