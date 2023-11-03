package com.projectlib.librarian.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

@DisplayName("Vendor model tests")
public class VendorModelTest {

    private Vendor vendor;

    @BeforeEach
    void setUp() {
        vendor = new Vendor();
    }

    @Test
    @DisplayName("Get ID")
    public void testGetId() {
        vendor.setId(1L);
        assertEquals(1L, vendor.getId());
    }

    @Test
    @DisplayName("Get Name")
    public void testGetName() {
        vendor.setName("VendorName");
        assertEquals("VendorName", vendor.getName());
    }

    @Test
    @DisplayName("Get Status")
    public void testGetStatus() {
        vendor.setStatus(true);
        assertEquals(true, vendor.getStatus());
    }

    @Test
    @DisplayName("Get Books")
    public void testGetBooks() {
        Set<Book> books = new HashSet<>();
        Book book1 = new Book();
        Book book2 = new Book();
        books.add(book1);
        books.add(book2);

        vendor.setBooks(books);

        assertEquals(2, vendor.getBooks().size());
    }

    @Test
    @DisplayName("Set ID")
    public void testSetId() {
        vendor.setId(1L);
        assertEquals(1L, vendor.getId());
    }

    @Test
    @DisplayName("Set Name")
    public void testSetName() {
        vendor.setName("VendorName");
        assertEquals("VendorName", vendor.getName());
    }

    @Test
    @DisplayName("Set Status")
    public void testSetStatus() {
        vendor.setStatus(true);
        assertEquals(true, vendor.getStatus());
    }

    @Test
    @DisplayName("Set Books")
    public void testSetBooks() {
        Set<Book> books = new HashSet<>();
        Book book1 = new Book();
        Book book2 = new Book();
        books.add(book1);
        books.add(book2);

        vendor.setBooks(books);

        assertEquals(2, vendor.getBooks().size());
    }
}
