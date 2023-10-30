package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/findAll")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/find/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping("/save")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping("/update/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book);
    }

    @DeleteMapping("/delete/{id}")
    public void deletebook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}
