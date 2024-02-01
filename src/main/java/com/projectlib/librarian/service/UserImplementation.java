package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.UserDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.mapper.UserMapper;

import com.projectlib.librarian.model.UserModel;
import com.projectlib.librarian.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserImplementation implements UserInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " does not exist."));
        return UserMapper.convertToDTO(user);
    }

    @Override
    public String createUser(UserDTO userDTO) {
        UserModel user = UserMapper.convertToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return "User created successfully.";
    }

    @Override
    public String updateUser(Long id, UserDTO updatedUserDTO) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " does not exist."));

        existingUser.setName(updatedUserDTO.getName());
        existingUser.setSurname(updatedUserDTO.getSurname());
        existingUser.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword()));

        if (!existingUser.getEmail().equals(updatedUserDTO.getEmail())) {
            existingUser.setEmail(updatedUserDTO.getEmail());
        }

        if (!existingUser.getUsername().equals(updatedUserDTO.getUsername())) {
            existingUser.setUsername(updatedUserDTO.getUsername());
        }

        try {
            userRepository.save(existingUser);
            return "User updated successfully.";
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Username or email already exists.");
        }
    }

    @Override
    public String setStatus(Long id, Boolean status) {
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID " + id + " does not exist."));

        existingUser.setStatus(status);
        userRepository.save(existingUser);
        return "User status updated successfully.";
    }

    @Override
    public String deleteUser(Long id) {
        userRepository.deleteById(id);
        return "User with ID " + id + " deleted successfully.";
    }
}
