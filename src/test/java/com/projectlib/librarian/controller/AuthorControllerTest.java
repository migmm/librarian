package com.projectlib.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.service.AuthorService;
import jakarta.transaction.Transactional;

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
import java.util.HashSet;


@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllAuthors() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(2)));
    }

    @Test
    public void testGetAuthorById() throws Exception {
        Long authorId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/find/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorId));
    }

    @Test
    public void testCreateAuthor() throws Exception {
        Author newAuthor = new Author(1L, "New Author", "New Last Name", true, new HashSet<>());

        when(authorService.createAuthor(Mockito.any(Author.class))).thenReturn("Author created successfully.");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newAuthor)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Author created successfully."));
    }

    @Test
    public void testUpdateAuthor() throws Exception {
        Long authorId = 1L;
        Author updatedAuthor = new Author(authorId, "Updated Author", "Updated Last Name", false, new HashSet<>());

        when(authorService.updateAuthor(authorId, updatedAuthor)).thenReturn("Author updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/update/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAuthor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Author updated successfully."));
    }

    @Test
    public void testSetStatus() throws Exception {
        Long authorId = 1L;
        Author updatedAuthor = new Author(authorId, "Author 1", "Last Name 1", false, new HashSet<>());

        when(authorService.setStatus(authorId, updatedAuthor)).thenReturn("Author status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/authors/setstatus/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAuthor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Author status updated successfully."));
    }

    @Test
    @Transactional
    public void testDeleteAuthor() throws Exception {
        Long authorId = 2L;

        when(authorService.deleteAuthor(authorId)).thenReturn("Author with ID " + authorId + " deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/authors/delete/{id}", authorId)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Author with ID " + authorId + " deleted successfully."));
    }
}
