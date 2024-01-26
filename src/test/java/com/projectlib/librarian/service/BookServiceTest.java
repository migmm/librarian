package com.projectlib.librarian.service;
import com.projectlib.librarian.helper.FilesHelper;
import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Book service tests")
class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private FilesHelper filesHelper;

    @InjectMocks
    private BookService bookService;

    @Test
    void getAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(books, result);
    }

    @Test
    @DisplayName("Get book by ID (existing book)")
    void getBookById_existingBook() {
        Book expectedBook = new Book();
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(expectedBook));

        Book result = bookService.getBookById(1L);

        assertEquals(expectedBook, result);
    }

    @Test
    @DisplayName("Get book by ID (non existing book)")
    void getBookById_nonExistingBook() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        Book result = bookService.getBookById(1L);

        assertNull(result);
    }

    @Test
    @DisplayName("Create a book")
    void createBook() throws IOException {
        Book book = new Book();
        List<MultipartFile> images = new ArrayList<>();
        images.add(new MockMultipartFile("image", "test.jpg", "image/jpeg", new byte[0]));

        when(filesHelper.saveImageToServer(any())).thenReturn("test.jpg");
        when(bookRepository.save(any())).thenReturn(book);

        String result = bookService.createBook(book, images);

        assertEquals("Book created successfully.", result);
        assertEquals("test.jpg", book.getImages().get(0));
    }

    @Test
    @DisplayName("Update a book")
    void updateBook() throws IOException {
        Long bookId = 1L;
        Book existingBook = new Book();
        Book updatedBook = new Book();
        List<MultipartFile> newImages = new ArrayList<>();

        lenient().when(filesHelper.saveImageToServer(any())).thenReturn("test.jpg");

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(existingBook));

        String result = bookService.updateBook(bookId, updatedBook, newImages);

        verify(filesHelper, times(newImages.size())).saveImageToServer(any());

        if (existingBook.getImages() != null && !existingBook.getImages().isEmpty()) {
            verify(filesHelper, times(1)).deleteImagesFromServer(anyList());
        } else {
            verify(filesHelper, never()).deleteImagesFromServer(anyList());
        }

        verify(bookRepository, times(1)).save(existingBook);

        assertEquals(updatedBook.getISBN(), existingBook.getISBN());
        assertEquals(updatedBook.getTitle(), existingBook.getTitle());
        assertEquals("Book updated successfully.", result);
    }

    @Test
    @DisplayName("Test borrow a book")
    void borrowBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setBooks_left(2);
        existingBook.setBorrowed_books(0);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));

        String result = bookService.borrowBook(bookId, existingBook);

        assertEquals("Book borrowed successfully.", result);
        assertEquals(1, existingBook.getBorrowed_books().intValue());
        assertEquals(1, existingBook.getBooks_left().intValue());
        assertTrue(existingBook.getStatus());

        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    @DisplayName("Test return a book")
    void returnBook() {
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setBorrowed_books(1);
        existingBook.setBooks_left(0);

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(existingBook));
        String result = bookService.returnBook(bookId, existingBook);

        assertEquals("Book returned successfully.", result);
        assertEquals(0, existingBook.getBorrowed_books());
        assertEquals(1, existingBook.getBooks_left());

        assertTrue(existingBook.getStatus());

        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    @DisplayName("Test Set status of a book (logical deletion)")
    void setStatus() {
        Long bookId = 1L;
        Book existingBook = new Book();
        existingBook.setStatus(true);

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(existingBook));
        String result = bookService.setStatus(bookId, existingBook);

        assertEquals("Book status updated successfully.", result);
        assertTrue(existingBook.getStatus().booleanValue());

        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    @DisplayName("Test delete a book")
    void deleteBook() {
        Long bookId = 1L;
        Book existingBook = new Book();

        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(existingBook));

        String result = bookService.deleteBook(bookId);

        assertEquals("Book with ID 1 deleted successfully.", result);
        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
