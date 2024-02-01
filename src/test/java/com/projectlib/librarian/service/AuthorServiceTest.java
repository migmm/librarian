package com.projectlib.librarian.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.projectlib.librarian.dto.AuthorDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.repository.AuthorRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AuthorServiceTest {

    @InjectMocks
    private AuthorImplementation authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    void findAllAuthorsTest() {

        Author author1 = mock(Author.class);
        Author author2 = mock(Author.class);
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        List<AuthorDTO> result = authorService.getAllAuthors();

        assertEquals(2, result.size());
    }

    @Test
    void findAllITest() {

        Author author1 = new Author();
        author1.setId(1L);
        author1.setName("John");
        author1.setSurname("Doe");
        author1.setStatus(true);

        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Jane");
        author2.setSurname("Smith");
        author2.setStatus(true);

        List<Author> authorList = Arrays.asList(author1, author2);
        when(authorRepository.findAll()).thenReturn(authorList);

        List<AuthorDTO> expected = authorList.stream()
                .map(author -> new AuthorDTO(author.getId(), author.getName(), author.getSurname(), author.getStatus()))
                .collect(Collectors.toList());

        List<AuthorDTO> result = authorService.getAllAuthors();

        assertNotNull(result);
        assertEquals(expected.size(), result.size());

        for (int i = 0; i < expected.size(); i++) {
            AuthorDTO expectedDTO = expected.get(i);
            AuthorDTO resultDTO = result.get(i);
            assertEquals(expectedDTO.getId(), resultDTO.getId());
            assertEquals(expectedDTO.getName(), resultDTO.getName());
            assertEquals(expectedDTO.getSurname(), resultDTO.getSurname());
            assertEquals(expectedDTO.getStatus(), resultDTO.getStatus());
        }
    }

    @Test
    void saveAuthorTest() {

        Author author = mock(Author.class);
        AuthorDTO authorDto = mock(AuthorDTO.class);
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        String result = authorService.createAuthor(authorDto);

        assertEquals("Author created successfully.", result);
    }

    @Test
    void deleteAuthorTest() {

        Long authorId = 1L;
        doNothing().when(authorRepository).deleteById(authorId);

        String result = authorService.deleteAuthor(authorId);

        assertEquals("Author with ID " + authorId + " deleted successfully.", result);
    }

    @Test
    void getAuthorByIdTest() {

        Long authorId = 1L;
        Author author = new Author();
        author.setId(authorId);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.getAuthorById(authorId);

        assertEquals(authorId, result.getId());
    }

    @Test
    void getAuthorByIdNotFoundTest() {

        Long authorId = 1L;
        when(authorRepository.findById(authorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authorService.getAuthorById(authorId));
    }

    @Test
    void updateAuthorNotFoundTest() {

        Long authorId = 1L;
        AuthorDTO updatedAuthorDTO = new AuthorDTO();
        updatedAuthorDTO.setId(authorId);

        assertThrows(NotFoundException.class, () -> authorService.updateAuthor(authorId, updatedAuthorDTO));
    }

    @Test
    void deleteAuthorNotFoundTest() {

        Long authorId = 1L;
        doThrow(new NotFoundException("Author with ID " + authorId + " does not exist.")).when(authorRepository)
                .deleteById(authorId);

        assertThrows(NotFoundException.class, () -> authorService.deleteAuthor(authorId));
    }
}