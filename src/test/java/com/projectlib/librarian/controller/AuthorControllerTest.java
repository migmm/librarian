package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.service.AuthorService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testGetAllAuthors() throws Exception {
        // Mock data
        List<Author> authors = new ArrayList<>();

        authors.add(new Author(1L, "Author 1", "New Last Name", true, new HashSet<>()));
        authors.add(new Author(2L, "Author 2", "New Last Name", true, new HashSet<>()));

        when(authorService.getAllAuthors()).thenReturn(authors);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Author 2"));

        verify(authorService, times(1)).getAllAuthors();
    }

    @Test
    public void testGetAuthorById() throws Exception {
        // Mock data
        Author author = new Author(1L, "Author 1", "New Last Name", true, new HashSet<>());
        Long authorId = 1L;

        when(authorService.getAuthorById(authorId)).thenReturn(author);

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/find/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Author 1"));

        verify(authorService, times(1)).getAuthorById(authorId);
    }

    @Test
    public void testCreateauthor() throws Exception {
        // Mock data
        Author newauthor = new Author(1L, "New Author", "New Last Name", true, new HashSet<>());

        when(authorService.createAuthor(newauthor)).thenReturn("Author created successfully.");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New author\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"authors_quantity\":5,"
                                + "\"borrowed_authors\":0,"
                                + "\"authors_left\":5,"
                                + "\"genre\":\"Adventure\","
                                + "\"status\":true,"
                                + "\"authors\":["
                                + "]"
                                + "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateauthor() throws Exception {
        // Mock data
        Long authorId = 1L;
        Author updatedauthor = new Author(1L, "New Author", "New Last Name", true, new HashSet<>());

        when(authorService.updateAuthor(authorId, updatedauthor)).thenReturn("Author updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/update/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New author\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"authors_quantity\":5,"
                                + "\"borrowed_authors\":0,"
                                + "\"authors_left\":5,"
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
        Long authorId = 1L;
        Author author = new Author(1L, "New Author", "New Last Name", true, new HashSet<>());

        when(authorService.setStatus(authorId, author)).thenReturn("author status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/setstatus/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteAuthor() throws Exception {
        // Mock data
        Long authorId = 1L;

        when(authorService.deleteAuthor(authorId)).thenReturn("author with ID " + authorId + " deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/delete/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(content().string("author with ID " + authorId + " deleted successfully."));

        verify(authorService, times(1)).deleteAuthor(authorId);
    }
}
