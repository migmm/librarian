package com.projectlib.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlib.librarian.dto.BookDTO;
import com.projectlib.librarian.mapper.BookMapper;
import com.projectlib.librarian.model.Book;

import com.projectlib.librarian.service.BookInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Tag(name = "Books", description = "Endpoints to work with books.")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookInterface bookInterface;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/findAll")
    @Operation(summary = "Get all books", description = "Get a complete list of all books (does not include which have setStatus=false)")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookInterface.getAllBooks();
        books.removeIf(book -> !book.getStatus());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get a book by ID", description = "Get a book with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookInterface.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new book", description = "Save a new book with full information.")
    public ResponseEntity<String> createBook(@Valid @RequestPart("book") String bookJson, @RequestPart("images") List<MultipartFile> images) throws IOException {
        BookDTO bookDTO = BookMapper.convertToDTO(objectMapper.readValue(bookJson, Book.class));
        String message = bookInterface.createBook(bookDTO, images);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a book", description = "Update a book information using the ID as param.")
    public ResponseEntity<String> updateBook(@Valid @PathVariable Long id, @RequestPart("book") String bookJson, @RequestPart(name = "images", required = false) List<MultipartFile> images) throws IOException {
        BookDTO bookDTO = BookMapper.convertToDTO(objectMapper.readValue(bookJson, Book.class));
        String message = bookInterface.updateBook(id, bookDTO, images);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/borrow/{id}")
    @Operation(summary = "borrow a book", description = "Borrow a book using the ID as param.")
    public ResponseEntity<String> borrowBook(@PathVariable Long id) {
        String message = bookInterface.borrowBook(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/return/{id}")
    @Operation(summary = "Return a book", description = "Return a book using the ID as param.")
    public ResponseEntity<String> returnBook(@PathVariable Long id) {
        String message = bookInterface.returnBook(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")
    @Operation(summary = "Set book status", description = "Set book status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> requestBody) {
        Boolean status = requestBody.get("status");
        if (status == null) {
            return new ResponseEntity<>("Invalid status value.", HttpStatus.BAD_REQUEST);
        }

        String message = bookInterface.setStatus(id, status);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an book", description = "Delete an book using the ID as param.")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        String message = bookInterface.deleteBook(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}