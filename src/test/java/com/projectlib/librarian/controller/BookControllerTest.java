package com.projectlib.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Book> mockBooks = new ArrayList<>();
        when(bookService.getAllBooks()).thenReturn(mockBooks);

        ResponseEntity<List<Book>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBooks, response.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book mockBook = new Book();
        when(bookService.getBookById(bookId)).thenReturn(mockBook);

        ResponseEntity<Book> response = bookController.getBookById(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBook, response.getBody());
        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    void createBook() throws IOException {
        String bookJson = "{\"id\":1,\"title\":\"New Book\",\"isbn\":123456,\"status\":true}";
        List<MultipartFile> images = Arrays.asList(new MockMultipartFile("image", "new.jpg", "image/jpeg", "New image".getBytes()));

        when(bookService.createBook(any(), eq(images)))
                .thenReturn("Book created successfully.");

        ResponseEntity<String> response = bookController.createBook(bookJson, images);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        verify(bookService, times(1)).createBook(any(), eq(images));
    }

    @Test
    void updateBook() throws IOException {
        Long bookId = 1L;
        String bookJson = "{\"id\":1,\"title\":\"Updated Book\",\"isbn\":789012,\"status\":true}";
        List<MultipartFile> images = Arrays.asList(new MockMultipartFile("image", "updated.jpg", "image/jpeg", "Updated image".getBytes()));

        ObjectMapper objectMapper = new ObjectMapper();
        Book expectedBook = objectMapper.readValue(bookJson, Book.class);

        when(bookService.updateBook(eq(bookId), any(), eq(images)))
                .thenReturn("Book updated successfully."); // Cambiado a un retorno exitoso

        ResponseEntity<String> response = bookController.updateBook(bookId, bookJson, images);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book updated successfully.", response.getBody());

        verify(bookService, times(1)).updateBook(eq(bookId), any(), eq(images));
    }

    @Test
    void borrowBook() {
        Long bookId = 1L;
        Book borrowedBook = new Book();

        when(bookService.borrowBook(eq(bookId), any())).thenReturn("Book borrowed successfully.");

        ResponseEntity<String> response = bookController.borrowBook(bookId, borrowedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book borrowed successfully.", response.getBody());

        verify(bookService, times(1)).borrowBook(bookId, borrowedBook);
    }

    @Test
    void returnBook() {
        Long bookId = 1L;
        Book returnedBook = new Book();

        when(bookService.returnBook(eq(bookId), any())).thenReturn("Book returned successfully.");

        ResponseEntity<String> response = bookController.returnBook(bookId, returnedBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book returned successfully.", response.getBody());

        verify(bookService, times(1)).returnBook(bookId, returnedBook);
    }

    @Test
    void setStatus() {
        Long bookId = 1L;
        Book statusBook = new Book();

        when(bookService.setStatus(eq(bookId), any())).thenReturn("Book status updated successfully.");

        ResponseEntity<String> response = bookController.setStatus(bookId, statusBook);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book status updated successfully.", response.getBody());

        verify(bookService, times(1)).setStatus(bookId, statusBook);
    }

    @Test
    void deleteBook() {
        Long bookId = 1L;

        when(bookService.deleteBook(eq(bookId))).thenReturn("Book with ID 1 deleted successfully.");

        ResponseEntity<String> response = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Book with ID 1 deleted successfully.", response.getBody());

        verify(bookService, times(1)).deleteBook(bookId);
    }
}