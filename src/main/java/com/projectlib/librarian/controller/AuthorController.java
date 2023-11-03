package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Endpoints to work with authors.")
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/findAll")
    @Operation(summary = "Get all authors", description = "Get a complete list of all authors (does not include which have setStatus=false)")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        authors.removeIf(author -> !author.getStatus());
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get an author by ID", description = "Get an author with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new author", description = "Save a new author with full information using the ID as param.")
    public ResponseEntity<String> createAuthor(@RequestBody Author author) {
        String message = authorService.createAuthor(author);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an author", description = "Update an author information using the ID as param.")
    public ResponseEntity<String> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        String message = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")
    @Operation(summary = "Set author status", description = "Set author status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Author author) {
        String message = authorService.setStatus(id, author);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an author", description = "Delete an author using the ID as param.")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        String message = authorService.deleteAuthor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}