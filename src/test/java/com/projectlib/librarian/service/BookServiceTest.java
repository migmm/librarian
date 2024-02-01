package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.BookDTO;
import com.projectlib.librarian.exception.NotFoundException;

import com.projectlib.librarian.mapper.BookMapper;
import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.repository.BookRepository;
import com.projectlib.librarian.helper.FilesHelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private FilesHelper filesHelper;

    @InjectMocks
    private BookImplementation bookService;

    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookMapper = new BookMapper();
    }

    @Test
    void getAllBooksTest() {

        Book book = new Book();
        book.setTitle("Test Book");
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));

        List<BookDTO> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
    }

    @Test
    void getBookByIdBookExistsTest() {

        Book book = new Book(1L, 1234567890123L, "Book 1", new Date(), 10, 0, 10, "Genre 1", true, new ArrayList<>(), null, null);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(book));

        BookDTO result = bookService.getBookById(1L);

        assertEquals("Book 1", result.getTitle());
    }

    @Test
    void getBookByIdBookNotExistsTest() {

        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void createBookTest() throws IOException {

        BookDTO bookDTO = new BookDTO(null, 1234567890123L, "Book 1", new Date(), 10, 0, 10, "Genre 1", true, new ArrayList<>(), null);

        List<MultipartFile> images = Arrays.asList(
                new MockMultipartFile("image1", "image1.png", "image/png", new byte[]{1, 2, 3}),
                new MockMultipartFile("image2", "image2.png", "image/png", new byte[]{4, 5, 6})
        );
        when(filesHelper.saveImageToServer(any(MultipartFile.class))).thenReturn("path/to/image.png");

        String result = bookService.createBook(bookDTO, images);

        assertEquals("Book created successfully.", result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookTest() throws IOException {

        BookDTO updatedBookDTO = new BookDTO(null, 1234567890123L, "Updated Book 1", new Date(), 15, 2, 13, "Genre 1", true, new ArrayList<>(), null);
        List<MultipartFile> newImages = Arrays.asList(
                new MockMultipartFile("newImage1", "newImage1.png", "image/png", new byte[]{7, 8, 9}),
                new MockMultipartFile("newImage2", "newImage2.png", "image/png", new byte[]{10, 11, 12})
        );
        Book existingBook = new Book(1L, 1234567890123L, "Book 1", new Date(), 10, 0, 10, "Genre 1", true, new ArrayList<>(), null, null);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(existingBook));
        when(filesHelper.saveImageToServer(any(MultipartFile.class))).thenReturn("path/to/newImage.png");

        String result = bookService.updateBook(1L, updatedBookDTO, newImages);

        assertEquals("Book updated successfully.", result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void borrowBookTest() {

        Book existingBook = new Book(1L, 1234567890123L, "Book 1", new Date(), 10, 0, 10, "Genre 1", true, new ArrayList<>(), null, null);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(existingBook));

        String result = bookService.borrowBook(1L);

        assertEquals("Book borrowed successfully.", result);
        assertEquals(1, existingBook.getBorrowed_books());
        assertEquals(9, existingBook.getBooks_left());
    }

    @Test
    void returnBookTest() {

        Book existingBook = new Book(1L, 1234567890123L, "Book 1", new Date(), 10, 2, 8, "Genre 1", true, new ArrayList<>(), null, null);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(existingBook));

        String result = bookService.returnBook(1L);

        assertEquals("Book returned successfully.", result);
        assertEquals(1, existingBook.getBorrowed_books());
        assertEquals(9, existingBook.getBooks_left());
    }

    @Test
    void setStatusTest() {

        Book existingBook = new Book(1L, 1234567890123L, "Book 1", new Date(), 10, 0, 10, "Genre 1", true, new ArrayList<>(), null, null);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(existingBook));

        String result = bookService.setStatus(1L, false);

        assertEquals("Book status updated successfully.", result);
        assertEquals(false, existingBook.getStatus());
    }

    @Test
    void deleteBookTest() {

        Book existingBook = new Book(1L, 1234567890123L, "Book 1", new Date(), 10, 0, 10, "Genre 1", true, new ArrayList<>(), null, null);
        when(bookRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(existingBook));

        String result = bookService.deleteBook(1L);

        assertEquals("Book with ID 1 deleted successfully.", result);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}