package com.projectlib.librarian.service;

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

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existingAuthor = getAuthorById(id);
        if (existingAuthor != null) {
            existingAuthor.setName(updatedAuthor.getName());
            existingAuthor.setSurname(updatedAuthor.getSurname());
            existingAuthor.setStatus(updatedAuthor.getStatus());
            return authorRepository.save(existingAuthor);
        }
        return null;
    }
}
