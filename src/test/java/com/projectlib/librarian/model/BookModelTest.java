package com.projectlib.librarian.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;


@DisplayName("Book model tests")
public class BookModelTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
    }

    @DisplayName("Get ID")
    @Test
    public void testGetId() {
        Long id = 1L;
        book.setId(id);
        assertEquals(id, book.getId());
    }
    
    @Test
    @DisplayName("Get ISBN")
    public void testGetISBN() {
        Long isbn = 9781234567890L;
        book.setISBN(isbn);
        assertEquals(isbn, book.getISBN());
    }

    @Test
    @DisplayName("Get Title")
    public void testGetTitle() {
        String title = "Sample Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    @DisplayName("Get Year")
    public void testGetYear() {
        Date year = new Date(49, 5, 8);
        book.setYear(year);
        assertEquals(year, book.getYear());
    }

    @Test
    @DisplayName("Get books quantity")
    public void testGetBooksQuantity() {
        int quantity = 50;
        book.setBooks_quantity(quantity);
        assertEquals(quantity, book.getBooks_quantity());
    }

    @Test
    @DisplayName("Get borrowed books")
    public void testGetBorrowedBooks() {
        int borrowed = 10;
        book.setBorrowed_books(borrowed);
        assertEquals(borrowed, book.getBorrowed_books());
    }

    @Test
    @DisplayName("Get books left")
    public void testGetBooksLeft() {
        int left = 40;
        book.setBooks_left(left);
        assertEquals(left, book.getBooks_left());
    }

    @Test
    @DisplayName("Get books genre")
    public void testGetGenre() {
        String genre = "Fiction";
        book.setGenre(genre);
        assertEquals(genre, book.getGenre());
    }

    @Test
    @DisplayName("Get books status")
    public void testSetStatus() {
        boolean status = true;
        book.setStatus(status);
        assertEquals(status, book.getStatus());
    }

    @DisplayName("Set ID")
    @Test
    public void testSetId() {
        Long id = 1L;
        book.setId(id);
        assertEquals(id, book.getId());
    }

    @DisplayName("Set ISBN")
    @Test
    public void testSetISBN() {
        Long isbn = 9781234567890L;
        book.setISBN(isbn);
        assertEquals(isbn, book.getISBN());
    }

    @Test
    @DisplayName("Set Title")
    public void testSetTitle() {
        String title = "Sample Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    @DisplayName("Set Year")
    public void testSetYear() {
        Date year = new Date(49, 5, 8);
        book.setYear(year);
        assertEquals(year, book.getYear());
    }

    @Test
    @DisplayName("Set books quantity")
    public void testSetBooksQuantity() {
        int quantity = 50;
        book.setBooks_quantity(quantity);
        assertEquals(quantity, book.getBooks_quantity());
    }

    @Test
    @DisplayName("Set borrowed books")
    public void testSetBorrowedBooks() {
        int borrowed = 10;
        book.setBorrowed_books(borrowed);
        assertEquals(borrowed, book.getBorrowed_books());
    }

    @Test
    @DisplayName("Set books left")
    public void testSetBooksLeft() {
        int left = 40;
        book.setBooks_left(left);
        assertEquals(left, book.getBooks_left());
    }

    @Test
    @DisplayName("Set books genre")
    public void testSetGenre() {
        String genre = "Fiction";
        book.setGenre(genre);
        assertEquals(genre, book.getGenre());
    }

    @Test
    @DisplayName("Set books status")
    public void testGetStatus() {
        boolean status = true;
        book.setStatus(status);
        assertEquals(status, book.getStatus());
    }
}


