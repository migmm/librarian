package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> author = authorService.getAllAuthors();
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createAuthor(@RequestBody Author author) {
        String message = authorService.createAuthor(author);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        String message = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id) {
        String message = authorService.deleteAuthor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}