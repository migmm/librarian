package com.projectlib.librarian.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;


public class BookModelTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
    }

    @Test
    public void testGetId() {
        Long id = 1L;
        book.setId(id);
        assertEquals(id, book.getId());
    }

    @Test
    public void testSetId() {
        Long id = 1L;
        book.setId(id);
        assertEquals(id, book.getId());
    }

    @Test
    public void testGetISBN() {
        Long isbn = 9781234567890L;
        book.setISBN(isbn);
        assertEquals(isbn, book.getISBN());
    }

    @Test
    public void testSetISBN() {
        Long isbn = 9781234567890L;
        book.setISBN(isbn);
        assertEquals(isbn, book.getISBN());
    }

    @Test
    public void testGetTitle() {
        String title = "Sample Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    public void testSetTitle() {
        String title = "Sample Book Title";
        book.setTitle(title);
        assertEquals(title, book.getTitle());
    }

    @Test
    public void testGetYear() {
        Date year = new Date(49, 5, 8);
        book.setYear(year);
        assertEquals(year, book.getYear());
    }

    @Test
    public void testSetYear() {
        Date year = new Date(49, 5, 8);
        book.setYear(year);
        assertEquals(year, book.getYear());
    }

    @Test
    public void testGetBooksQuantity() {
        int quantity = 50;
        book.setBooks_quantity(quantity);
        assertEquals(quantity, book.getBooks_quantity());
    }

    @Test
    public void testSetBooksQuantity() {
        int quantity = 50;
        book.setBooks_quantity(quantity);
        assertEquals(quantity, book.getBooks_quantity());
    }

    @Test
    public void testGetBorrowedBooks() {
        int borrowed = 10;
        book.setBorrowed_books(borrowed);
        assertEquals(borrowed, book.getBorrowed_books());
    }

    @Test
    public void testSetBorrowedBooks() {
        int borrowed = 10;
        book.setBorrowed_books(borrowed);
        assertEquals(borrowed, book.getBorrowed_books());
    }

    @Test
    public void testGetBooksLeft() {
        int left = 40;
        book.setBooks_left(left);
        assertEquals(left, book.getBooks_left());
    }

    @Test
    public void testSetBooksLeft() {
        int left = 40;
        book.setBooks_left(left);
        assertEquals(left, book.getBooks_left());
    }

    @Test
    public void testGetGenre() {
        String genre = "Fiction";
        book.setGenre(genre);
        assertEquals(genre, book.getGenre());
    }

    @Test
    public void testSetGenre() {
        String genre = "Fiction";
        book.setGenre(genre);
        assertEquals(genre, book.getGenre());
    }

    @Test
    public void testGetStatus() {
        boolean status = true;
        book.setStatus(status);
        assertEquals(status, book.getStatus());
    }

    @Test
    public void testSetStatus() {
        boolean status = true;
        book.setStatus(status);
        assertEquals(status, book.getStatus());
    }
}


