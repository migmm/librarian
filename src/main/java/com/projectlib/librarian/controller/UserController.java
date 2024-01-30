package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.UserDTO;
import com.projectlib.librarian.model.UserModel;
import com.projectlib.librarian.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Endpoints to work with users.")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    @Operation(summary = "Get all users", description = "Get a complete list of all users (does not include which have setStatus=false)")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        users.removeIf(user -> !user.getStatus());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get a user by ID", description = "Get a user with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<UserModel>  getUserById(@PathVariable Long id) {
        UserModel user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new user", description = "Save a new user with full information using the ID as param.")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO) {
        String message = userService.createUser(userDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a user", description = "Update a user information using the ID as param.")
    public ResponseEntity<String> updateUser(@Valid @PathVariable Long id, @RequestBody UserDTO userDTO) {
        String message = userService.updateUser(id, userDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")
    @Operation(summary = "Set user status", description = "Set user status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> requestBody) {
        Boolean status = requestBody.get("status");
        if (status == null) {
            return new ResponseEntity<>("Invalid status value.", HttpStatus.BAD_REQUEST);
        }
        String message = userService.setStatus(id, status);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user using the ID as param.")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}