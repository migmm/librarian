package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.AuthorDTO;
import java.util.List;

public interface AuthorInterface {
    List<AuthorDTO> getAllAuthors();
    AuthorDTO getAuthorById(Long id);
    String createAuthor(AuthorDTO authorDTO);
    String updateAuthor(Long id, AuthorDTO updatedAuthorDTO);
    String setStatus(Long id, Boolean status);
    String deleteAuthor(Long id);
}
