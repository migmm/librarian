package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.AuthorDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.repository.AuthorRepository;
import com.projectlib.librarian.mapper.AuthorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthorImplementation implements AuthorInterface {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(AuthorMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with ID " + id + " does not exist."));
        return AuthorMapper.convertToDTO(author);
    }

    @Override
    public String createAuthor(AuthorDTO authorDTO) {
        Author author = AuthorMapper.convertToEntity(authorDTO);
        authorRepository.save(author);
        return "Author created successfully.";
    }

    @Override
    public String updateAuthor(Long id, AuthorDTO updatedAuthorDTO) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with ID " + id + " does not exist."));

        Author updatedAuthor = AuthorMapper.convertToEntity(updatedAuthorDTO);
        updatedAuthor.setId(existingAuthor.getId());

        authorRepository.save(updatedAuthor);
        return "Author updated successfully.";
    }

    @Override
    public String setStatus(Long id, Boolean status) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with ID " + id + " does not exist."));

        existingAuthor.setStatus(status);
        authorRepository.save(existingAuthor);
        return "Author status updated successfully.";
    }

    @Override
    public String deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        return "Author with ID " + id + " deleted successfully.";
    }
}
