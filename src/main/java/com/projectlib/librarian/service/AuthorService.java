package com.projectlib.librarian.service;

import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.Author;
import com.projectlib.librarian.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {

        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with ID " + id + " does not exist."));
    }

    public String createAuthor(Author author) {
        authorRepository.save(author);
        return "Author created successfully.";
    }

    public String updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = getAuthorById(id);
        if (existingAuthor != null) {
            existingAuthor.setName(updatedAuthor.getName());
            existingAuthor.setSurname(updatedAuthor.getSurname());
            existingAuthor.setStatus(updatedAuthor.getStatus());
            authorRepository.save(existingAuthor);
            return "Author updated successfully.";
        }
        throw new NotFoundException("Author with ID " + id + " does not exist.");
    }

    public String setStatus(Long id, Author updatedAuthor) {
        Author existingAuthor = getAuthorById(id);
        if (existingAuthor != null) {
            existingAuthor.setStatus(updatedAuthor.getStatus());
            authorRepository.save(existingAuthor);
            return "Author status updated successfully.";
        }
        throw new NotFoundException("Author with ID " + id + " does not exist.");
    }

    public String deleteAuthor(Long id) {
        Author existingAuthor = getAuthorById(id);
        if (existingAuthor != null) {
        authorRepository.deleteById(id);
        return "Author with ID " + id + " deleted successfully.";
        }
        throw new NotFoundException("Author with ID " + id + " does not exist.");
    }
}
