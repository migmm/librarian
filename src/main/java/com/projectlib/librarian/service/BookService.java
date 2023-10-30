package com.projectlib.librarian.service;

import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public String createBook(Book book) {
        bookRepository.save(book);
        return "Book created successfully.";
    }

    public String updateBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            existingBook.setISBN(updatedBook.getISBN());
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setYear(updatedBook.getYear());
            existingBook.setBooks_quantity(updatedBook.getBooks_quantity());
            existingBook.setBorrowed_books(updatedBook.getBorrowed_books());
            existingBook.setBooks_left(updatedBook.getBooks_left());
            existingBook.setGenre(updatedBook.getGenre());
            existingBook.setStatus(updatedBook.getStatus());
            bookRepository.save(existingBook);
            return "Book updated successfully.";
        }
        return "Book does not exist.";
    }

    public String setStatus(Long id, Book setStatus) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            existingBook.setStatus(setStatus.getStatus());
            bookRepository.save(existingBook);
            return "Book status updated successfully.";
        }
        return "Book does not exist.";
    }

    public String deleteBook(Long id) {
        bookRepository.deleteById(id);
        return "Book with ID " + id + " deleted successfully.";
    }
}
