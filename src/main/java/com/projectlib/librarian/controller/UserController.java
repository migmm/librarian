package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.User_table;
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
    public ResponseEntity<List<User_table>> getAllUsers() {
        List<User_table> users = userService.getAllUsers();
        users.removeIf(user -> !user.getStatus());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get a user by ID", description = "Get a user with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<User_table>  getUserById(@PathVariable Long id) {
        User_table user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new user", description = "Save a new user with full information using the ID as param.")
    public ResponseEntity<String> createUser(@Valid @RequestBody User_table user) {
        String message = userService.createUser(user);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a user", description = "Update a user information using the ID as param.")
    public ResponseEntity<String> updateUser(@Valid @PathVariable Long id, @RequestBody User_table user) {
        String message = userService.updateUser(id, user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")
    @Operation(summary = "Set user status", description = "Set user status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> statusMap) {
        Boolean newStatus = statusMap.get("newStatus");
        String message = userService.setStatus(id, newStatus);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a user", description = "Delete a user using the ID as param.")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        String message = userService.deleteUser(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}