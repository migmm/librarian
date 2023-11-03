package com.projectlib.librarian.model;

import com.projectlib.librarian.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Author model tests")
public class AuthorModelTest {

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
    }


    @Test
    @DisplayName("Get ID")
    public void testGetId() {
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    @DisplayName("Get Name")
    public void testGetName() {
        author.setName("John");
        assertEquals("John", author.getName());
    }

    @Test
    @DisplayName("Get Surname")
    public void testGetSurname() {
        author.setSurname("Doe");
        assertEquals("Doe", author.getSurname());
    }

    @Test
    @DisplayName("Get Status")
    public void testGetStatus() {
        author.setStatus(true);
        assertEquals(true, author.getStatus());
    }

    @Test
    @DisplayName("Vendor controllers tests")
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
    @DisplayName("Set ID")
    public void testSetId() {
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    @DisplayName("Set Name")
    public void testSetName() {
        author.setName("John");
        assertEquals("John", author.getName());
    }

    @Test
    @DisplayName("Set Surname")
    public void testSetSurname() {
        author.setSurname("Doe");
        assertEquals("Doe", author.getSurname());
    }

    @Test
    @DisplayName("Set Status")
    public void testSetStatus() {
        author.setStatus(true);
        assertEquals(true, author.getStatus());
    }

    @Test
    @DisplayName("Set Books")
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
