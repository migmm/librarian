package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AuthorControllerTest {
    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAuthors() {
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(1L, "Author 1", "Last Name 1", true, new HashSet<>()));

        when(authorService.getAllAuthors()).thenReturn(authors);

        ResponseEntity<List<Author>> response = authorController.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetAuthorById() {
        Long authorId = 1L;
        Author author = new Author(authorId, "Author 1", "Last Name 1", true, new HashSet<>());

        when(authorService.getAuthorById(authorId)).thenReturn(author);

        ResponseEntity<Author> response = authorController.getAuthorById(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authorId, response.getBody().getId());
    }

    @Test
    public void testCreateAuthor() {
        Author newAuthor = new Author(1L, "New Author", "New Last Name", true, new HashSet<>());

        when(authorService.createAuthor(Mockito.any(Author.class))).thenReturn("Author created successfully.");

        ResponseEntity<String> response = authorController.createAuthor(newAuthor);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Author created successfully.", response.getBody());
    }

    @Test
    public void testUpdateAuthor() {
        Long authorId = 1L;
        Author updatedAuthor = new Author(authorId, "Updated Author", "Updated Last Name", false, new HashSet<>());

        when(authorService.updateAuthor(authorId, updatedAuthor)).thenReturn("Author updated successfully.");

        ResponseEntity<String> response = authorController.updateAuthor(authorId, updatedAuthor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author updated successfully.", response.getBody());
    }

    @Test
    public void testSetStatus() {
        Long authorId = 1L;
        Author updatedAuthor = new Author(authorId, "Author 1", "Last Name 1", false, new HashSet<>());

        when(authorService.setStatus(authorId, updatedAuthor)).thenReturn("Author status updated successfully.");

        ResponseEntity<String> response = authorController.setStatus(authorId, updatedAuthor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author status updated successfully.", response.getBody());
    }

    @Test
    public void testDeleteAuthor() {
        Long authorId = 1L;

        when(authorService.deleteAuthor(authorId)).thenReturn("Author with ID 1 deleted successfully.");

        ResponseEntity<String> response = authorController.deleteAuthor(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author with ID 1 deleted successfully.", response.getBody());
    }

    @Test
    public void testDeleteAuthorAuthorDoesNotExist() {
        Long authorId = 1L;

        when(authorService.deleteAuthor(authorId)).thenReturn("Author does not exist.");

        ResponseEntity<String> response = authorController.deleteAuthor(authorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Author does not exist.", response.getBody());
    }
}
