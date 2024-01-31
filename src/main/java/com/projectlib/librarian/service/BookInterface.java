package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.BookDTO;
import io.jsonwebtoken.io.IOException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface BookService {
    List<BookDTO> getAllBooks();
    BookDTO getBookById(Long id);
    String createBook(BookDTO book, List<MultipartFile> imagePaths) throws IOException;
    String updateBook(Long id, BookDTO updatedBook, List<MultipartFile> newImages) throws IOException;
    String borrowBook(Long id);
    String returnBook(Long id);
    String setStatus(Long id, Boolean status);
    String deleteBook(Long id);
}
