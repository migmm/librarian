package com.projectlib.librarian.service;

import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@DisplayName("Book service tests")
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get bool by ID")
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Book retrievedBook = bookService.getBookById(bookId);

        assertEquals(book, retrievedBook);
    }

    @Test
    @DisplayName("Get book by ID when not exists")
    public void testGetBookByIdWhenNotExists() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book retrievedBook = bookService.getBookById(bookId);

        assertNull(retrievedBook);
    }

    @Test
    @DisplayName("Create a book")
    public void testCreateBook() {
        Book book = new Book();
        when(bookRepository.save(book)).thenReturn(book);

        String message = bookService.createBook(book);

        assertEquals("Book created successfully.", message);
    }

    @Test
    @DisplayName("Update a book")
    public void testUpdateBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        Book updatedBook = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        String message = bookService.updateBook(bookId, updatedBook);

        assertEquals("Book updated successfully.", message);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    @DisplayName("Test update a book when not exists")
    public void testUpdateBookWhenNotExists() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        String message = bookService.updateBook(bookId, updatedBook);

        assertEquals("Book does not exist.", message);
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test borrow a book")
    public void testBorrowBook() {
        Long bookId = 1L;

        Book existingBook = new Book();
        existingBook.setBooks_left(1);
        existingBook.setBorrowed_books(0);
        existingBook.setStatus(true);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        Book updatedBook = new Book();
        String message = bookService.borrowBook(bookId, updatedBook);

        assertEquals("Book borrowed successfully.", message);
        assertEquals(1, existingBook.getBorrowed_books());
        assertEquals(0, existingBook.getBooks_left());
        assertFalse(existingBook.getStatus());
        verify(bookRepository, times(1)).save(existingBook);
    }


    @Test
    @DisplayName("Test borrow a book when not exists")
    public void testBorrowBookWhenNotExists() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book updatedBook = new Book();
        String message = bookService.borrowBook(bookId, updatedBook);

        assertEquals("Book does not exist.", message);
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test return a book")
    public void testReturnBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setBooks_left(4);
        existingBook.setBorrowed_books(2);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        Book updatedBook = new Book();
        String message = bookService.returnBook(bookId, updatedBook);

        assertEquals("Book returned successfully.", message);
        assertEquals(1, existingBook.getBorrowed_books());
        assertEquals(5, existingBook.getBooks_left());
        assertTrue(existingBook.getStatus());
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    @DisplayName("Test return a book when not exists")
    public void testReturnBookWhenNotExists() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Book updatedBook = new Book();
        String message = bookService.returnBook(bookId, updatedBook);

        assertEquals("Book does not exist.", message);
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test Set status of a book (logical deletion)")
    public void testSetStatus() {
        Long bookId = 1L;
        Book existingBook = new Book();
        Book setStatus = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        String message = bookService.setStatus(bookId, setStatus);

        assertEquals("Book status updated successfully.", message);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    public void testSetStatusWhenNotExists() {
        Long bookId = 1L;
        Book setStatus = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        String message = bookService.setStatus(bookId, setStatus);

        assertEquals("Book does not exist.", message);
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Test delete a book")
    public void testDeleteBook() {
        Long bookId = 1L;
        when(bookRepository.existsById(bookId)).thenReturn(true);

        Book existingBook = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        String message = bookService.deleteBook(bookId);

        assertEquals("Book with ID 1 deleted successfully.", message);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Test delete a book when not exists")
    public void testDeleteBookWhenNotExists() {
        Long bookId = 0L;
        when(bookRepository.existsById(bookId)).thenReturn(false);

        String message = bookService.deleteBook(bookId);

        assertEquals("Book does not exist.", message);
        verify(bookRepository, never()).deleteById(any());
    }
}
