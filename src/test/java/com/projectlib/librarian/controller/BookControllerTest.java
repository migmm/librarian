package com.projectlib.librarian.controller;

import com.projectlib.librarian.controller.BookController;
import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        // Mock data
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, 1234567890L, "Book 1", null, 10, 5, 5, "Fiction", true, null, null));
        books.add(new Book(2L, 9876543210L, "Book 2", null, 8, 2, 6, "Mystery", true, null, null));

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() throws Exception {
        // Mock data
        Book book = new Book(1L, 1234567890L, "Book 1", null, 10, 5, 5, "Fiction", true, null, null);
        Long bookId = 1L;

        when(bookService.getBookById(bookId)).thenReturn(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/find/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"));

        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    public void testCreateBook() throws Exception {
        // Mock data
        Book newBook = new Book(3L, 9876543210L, "New Book", null, 5, 0, 5, "Adventure", true, null, null);

        when(bookService.createBook(newBook)).thenReturn("Book created successfully.");

        mockMvc.perform(MockMvcRequestBuilders.post("/books/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                        + "\"id\":3,"
                        + "\"isbn\":0,"
                        + "\"title\":\"New Book\","
                        + "\"year\":\"2023-11-02T21:02:24.982Z\","
                        + "\"books_quantity\":5,"
                        + "\"borrowed_books\":0,"
                        + "\"books_left\":5,"
                        + "\"genre\":\"Adventure\","
                        + "\"status\":true,"
                        + "\"authors\":["
                        + "]"
                        + "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateBook() throws Exception {
        // Mock data
        Long bookId = 1L;
        Book updatedBook = new Book(1L, 1234567890L, "Updated Book", null, 15, 5, 10, "Fiction", true, null, null);

        when(bookService.updateBook(bookId, updatedBook)).thenReturn("Book updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/update/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New Book\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"books_quantity\":5,"
                                + "\"borrowed_books\":0,"
                                + "\"books_left\":5,"
                                + "\"genre\":\"Adventure\","
                                + "\"status\":true,"
                                + "\"authors\":["
                                + "]"
                                + "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testBorrowBook() throws Exception {
        // Mock data
        Long bookId = 1L;
        Book borrowedBook = new Book(1L, 1234567890L, "Book 1", null, 10, 6, 4, "Fiction", true, null, null);

        when(bookService.borrowBook(bookId, borrowedBook)).thenReturn("Book borrowed successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/borrrow/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New Book\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"books_quantity\":5,"
                                + "\"borrowed_books\":0,"
                                + "\"books_left\":5,"
                                + "\"genre\":\"Adventure\","
                                + "\"status\":true,"
                                + "\"authors\":["
                                + "]"
                                + "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testReturnBook() throws Exception {
        // Mock data
        Long bookId = 1L;
        Book returnedBook = new Book(1L, 1234567890L, "Book 1", null, 10, 4, 6, "Fiction", true, null, null);

        when(bookService.returnBook(bookId, returnedBook)).thenReturn("Book returned successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/return/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New Book\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"books_quantity\":5,"
                                + "\"borrowed_books\":0,"
                                + "\"books_left\":5,"
                                + "\"genre\":\"Adventure\","
                                + "\"status\":true,"
                                + "\"authors\":["
                                + "]"
                                + "}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSetStatus() throws Exception {
        // Mock data
        Long bookId = 1L;
        Book book = new Book(1L, 1234567890L, "Book 1", null, 10, 4, 6, "Fiction", true, null, null);

        when(bookService.setStatus(bookId, book)).thenReturn("Book status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/books/setstatus/{id}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Mock data
        Long bookId = 1L;

        when(bookService.deleteBook(bookId)).thenReturn("Book with ID " + bookId + " deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(content().string("Book with ID " + bookId + " deleted successfully."));

        verify(bookService, times(1)).deleteBook(bookId);
    }
}
