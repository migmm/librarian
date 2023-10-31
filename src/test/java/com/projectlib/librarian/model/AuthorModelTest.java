package com.projectlib.librarian.model;

import com.projectlib.librarian.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthorModelTest {

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
    }

    @Test
    public void testGetId() {
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    public void testGetName() {
        author.setName("John");
        assertEquals("John", author.getName());
    }

    @Test
    public void testGetSurname() {
        author.setSurname("Doe");
        assertEquals("Doe", author.getSurname());
    }

    @Test
    public void testGetStatus() {
        author.setStatus(true);
        assertEquals(true, author.getStatus());
    }

    @Test
    public void testGetBooks() {
        Set<Book> books = new HashSet<>();
        Book book1 = new Book();
        Book book2 = new Book();
        books.add(book1);
        books.add(book2);

        author.setBooks(books);

        assertEquals(2, author.getBooks().size());
    }

    @Test
    public void testSetId() {
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    public void testSetName() {
        author.setName("John");
        assertEquals("John", author.getName());
    }

    @Test
    public void testSetSurname() {
        author.setSurname("Doe");
        assertEquals("Doe", author.getSurname());
    }

    @Test
    public void testSetStatus() {
        author.setStatus(true);
        assertEquals(true, author.getStatus());
    }

    @Test
    public void testSetBooks() {
        Set<Book> books = new HashSet<>();
        Book book1 = new Book();
        Book book2 = new Book();
        books.add(book1);
        books.add(book2);

        author.setBooks(books);

        assertEquals(2, author.getBooks().size());
    }
}
