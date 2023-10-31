package com.projectlib.librarian.controller;
import com.projectlib.librarian.controller.BookController;
import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = new ArrayList<>();
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>>response = bookController.getAllBooks();

        assertEquals(books, response.getBody());
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookService.getBookById(bookId)).thenReturn(book);

        ResponseEntity<Book> response = bookController.getBookById(bookId);

        assertEquals(book, response.getBody());
    }

    @Test
    public void testCreateBook() {
        Book book = new Book();
        when(bookService.createBook(book)).thenReturn("Book created successfully.");

        ResponseEntity<String> response = bookController.createBook(book);

        assertEquals("Book created successfully.", response.getBody());
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        when(bookService.updateBook(bookId, updatedBook)).thenReturn("Book updated successfully.");

        ResponseEntity<String> response = bookController.updateBook(bookId, updatedBook);

        assertEquals("Book updated successfully.", response.getBody());
    }

    @Test
    public void testBorrowBook() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookService.borrowBook(bookId, book)).thenReturn("Book borrowed successfully.");

        ResponseEntity<String> response = bookController.borrowBook(bookId, book);

        assertEquals("Book borrowed successfully.", response.getBody());
    }

    @Test
    public void testReturnBook() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookService.returnBook(bookId, book)).thenReturn("Book returned successfully.");

        ResponseEntity<String> response = bookController.returnBook(bookId, book);

        assertEquals("Book returned successfully.", response.getBody());
    }

    @Test
    public void testSetStatus() {
        Long bookId = 1L;
        Book setStatus = new Book();
        when(bookService.setStatus(bookId, setStatus)).thenReturn("Book status updated successfully.");

        ResponseEntity<String> response = bookController.setStatus(bookId, setStatus);

        assertEquals("Book status updated successfully.", response.getBody());
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;
        when(bookService.deleteBook(bookId)).thenReturn("Book with ID 1 deleted successfully.");

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertEquals("Book with ID 1 deleted successfully.", response.getBody());
    }

    @Test
    public void testDeleteBookWhenBookDoesNotExist() {
        Long bookId = 1L;
        when(bookService.deleteBook(bookId)).thenReturn("Book does not exist.");

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertEquals("Book does not exist.", response.getBody());
    }
}
