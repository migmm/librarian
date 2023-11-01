package com.projectlib.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.service.BookService;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.HashSet;


@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Book sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setISBN(1234567890L);
        sampleBook.setTitle("Sample Book");
        sampleBook.setYear(new Date());
        sampleBook.setBooks_quantity(10);
        sampleBook.setBorrowed_books(0);
        sampleBook.setBooks_left(10);
        sampleBook.setGenre("Fiction");
        sampleBook.setStatus(true);
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllBooks() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/books/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(2)));
    }

    @Test
    public void testGetBookById() throws Exception {
        Long bookId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/books/find/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookId));
    }

    @Test
    @Transactional
    public void testCreateBook() throws Exception {
        Book newBook = new Book();
        newBook.setId(1L);
        newBook.setISBN(9782455564935L);
        newBook.setTitle("New Book");
        newBook.setYear(new Date());
        newBook.setBooks_quantity(10);
        newBook.setBorrowed_books(0);
        newBook.setBooks_left(10);
        newBook.setGenre("Fiction");
        newBook.setStatus(true);
        newBook.setVendor(new Vendor());
        newBook.setAuthors(new HashSet<>());

        when(bookService.createBook(Mockito.any(Book.class))).thenReturn("Book created successfully.");

        mockMvc.perform(MockMvcRequestBuilders.post("/books/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Book created successfully."));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setISBN(9782455564935L);
        updatedBook.setTitle("New Book");
        updatedBook.setYear(new Date());
        updatedBook.setBooks_quantity(10);
        updatedBook.setBorrowed_books(0);
        updatedBook.setBooks_left(10);
        updatedBook.setGenre("Fiction");
        updatedBook.setStatus(true);
        updatedBook.setVendor(new Vendor());
        updatedBook.setAuthors(new HashSet<>());

        when(bookService.updateBook(bookId, updatedBook)).thenReturn("Book updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/update/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book updated successfully."));
    }

    @Test
    public void testSetStatus() throws Exception {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setStatus(true);

        when(bookService.setStatus(bookId, updatedBook)).thenReturn("Book status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/setstatus/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book status updated successfully."));
    }

    @Test
    @Transactional
    public void testDeleteBook() throws Exception {
        Long bookId = 2L;

        when(bookService.deleteBook(bookId)).thenReturn("Book with ID " + bookId + " deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Book with ID " + bookId + " deleted successfully."));
    }
}