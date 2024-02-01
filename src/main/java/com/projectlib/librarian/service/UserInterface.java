package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.UserDTO;

import java.util.List;

public interface UserInterface {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long id);
    String createUser(UserDTO userDTO);
    String updateUser(Long id, UserDTO updatedUser);
    String setStatus(Long id, Boolean status);
    String deleteUser(Long id);
}
