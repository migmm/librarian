package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.AuthorDTO;
import com.projectlib.librarian.service.AuthorInterface;
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
@Tag(name = "Authors", description = "Endpoints to work with authors.")
@RequestMapping("/authors")
public class AuthorController {
    
    @Autowired
    private AuthorInterface authorInterface;

    @GetMapping("/findAll")
    @Operation(summary = "Get all authors", description = "Get a complete list of all authors (does not include which have setStatus=false)")
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorInterface.getAllAuthors();
        authors.removeIf(author -> !author.getStatus());
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get an author by ID", description = "Get an author with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id) {
        AuthorDTO author = authorInterface.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        String message = authorInterface.createAuthor(authorDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an author", description = "Update an author information using the ID as param.")
    public ResponseEntity<String> updateAuthor(@Valid @PathVariable Long id, @Valid @RequestBody AuthorDTO authorDTO) {
        String message = authorInterface.updateAuthor(id, authorDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")
    @Operation(summary = "Set author status", description = "Set author status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> requestBody) {
        Boolean status = requestBody.get("status");
        if (status == null) {
            return new ResponseEntity<>("Invalid status value. Status must be either true or false.", HttpStatus.BAD_REQUEST);
        }

        String message = authorInterface.setStatus(id, status);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an author", description = "Delete an author using the ID as param.")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        String message = authorInterface.deleteAuthor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
