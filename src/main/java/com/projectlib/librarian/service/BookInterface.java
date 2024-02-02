package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.BookDTO;
import io.jsonwebtoken.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface BookInterface {
    
    Page<BookDTO> getAllBooks(Pageable pageable);
    BookDTO getBookById(Long id);
    String createBook(BookDTO book, List<MultipartFile> imagePaths) throws IOException, java.io.IOException;
    String updateBook(Long id, BookDTO updatedBook, List<MultipartFile> newImages) throws IOException, java.io.IOException;
    String borrowBook(Long id);
    String returnBook(Long id);
    String setStatus(Long id, Boolean status);
    String deleteBook(Long id);
}
