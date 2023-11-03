package com.projectlib.librarian.service;

import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("Author service tests")
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create an author")
    public void testCreateAuthor() {
        Author newAuthor = new Author(1L, "New Author", "New Last Name", true, new HashSet<>());

        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(newAuthor);

        String message = authorService.createAuthor(newAuthor);

        assertEquals("Author created successfully.", message);
    }

    @Test
    @DisplayName("Update an author")
    public void testUpdateAuthor() {
        Long authorId = 1L;
        Author existingAuthor = new Author(authorId, "Author 1", "Last Name 1", true, new HashSet<>());
        Author updatedAuthor = new Author(authorId, "Updated Author", "Updated Last Name", false, new HashSet<>());

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(updatedAuthor);

        String message = authorService.updateAuthor(authorId, updatedAuthor);

        assertEquals("Author updated successfully.", message);
    }

    @Test
    @DisplayName("Set status (logical deletion)")
    public void testSetStatus() {
        Long authorId = 1L;
        Author existingAuthor = new Author(authorId, "Author 1", "Last Name 1", true, new HashSet<>());
        Author updatedAuthor = new Author(authorId, "Author 1", "Last Name 1", false, new HashSet<>());

        when(authorRepository.findById(authorId)).thenReturn(Optional.of(existingAuthor));
        when(authorRepository.save(Mockito.any(Author.class))).thenReturn(updatedAuthor);

        String message = authorService.setStatus(authorId, updatedAuthor);

        assertEquals("Author status updated successfully.", message);
    }

    @Test
    @DisplayName("Delete author")
    public void testDeleteAuthor() {
        Long authorId = 1L;

        when(authorRepository.existsById(authorId)).thenReturn(true);

        String message = authorService.deleteAuthor(authorId);

        assertEquals("Author with ID 1 deleted successfully.", message);
    }
}
