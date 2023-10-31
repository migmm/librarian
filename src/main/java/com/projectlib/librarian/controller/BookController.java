package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        books.removeIf(book -> !book.getStatus());
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        String message = bookService.createBook(book);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book book) {
        String message = bookService.updateBook(id, book);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/borrrow/{id}")
    public ResponseEntity<String> borrowBook(@PathVariable Long id, @RequestBody Book book) {
        String message = bookService.borrowBook(id, book);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable Long id, @RequestBody Book book) {
        String message = bookService.returnBook(id, book);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Book book) {
        String message = bookService.setStatus(id, book);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        String message = bookService.deleteBook(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}