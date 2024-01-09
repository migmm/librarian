package com.projectlib.librarian.service;

import com.projectlib.librarian.model.Book;
import com.projectlib.librarian.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.projectlib.librarian.helper.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FilesHelper filesHelper;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public String createBook(Book book, List<MultipartFile> imagePaths) {
        List<String> savedImagePaths = new ArrayList<>();

        for (MultipartFile multipartFile : imagePaths) {
            try {
                String savedImagePath = filesHelper.saveImageToServer(multipartFile);
                savedImagePaths.add(savedImagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        book.setImages(savedImagePaths);
        bookRepository.save(book);

        return "Book created successfully.";
    }

    public String updateBook(Long id, Book updatedBook, List<MultipartFile> newImages) {
            Book existingBook = getBookById(id);
            if (existingBook != null) {
                existingBook.setISBN(updatedBook.getISBN());
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setYear(updatedBook.getYear());
                existingBook.setBooks_quantity(updatedBook.getBooks_quantity());
                existingBook.setBorrowed_books(updatedBook.getBorrowed_books());
                existingBook.setBooks_left(updatedBook.getBooks_left());
                existingBook.setGenre(updatedBook.getGenre());
                existingBook.setStatus(updatedBook.getStatus());

                if (newImages != null && !newImages.isEmpty()) {
                    filesHelper.deleteImagesFromServer(existingBook.getImages());

                    List<String> savedImagePaths = new ArrayList<>();

                    for (MultipartFile multipartFile : newImages) {
                        try {
                            String savedImagePath = filesHelper.saveImageToServer(multipartFile);
                            savedImagePaths.add(savedImagePath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    existingBook.setImages(savedImagePaths);
                }

                bookRepository.save(existingBook);
                return "Book updated successfully.";
            }
            return "Book does not exist.";
    }

    public String borrowBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            existingBook.setBorrowed_books(existingBook.getBorrowed_books() + 1);
            existingBook.setBooks_left(existingBook.getBooks_left() - 1);

            if (existingBook.getBooks_left() == 0) {
                existingBook.setStatus(false);
            } else {
                existingBook.setStatus(true);
            }

            bookRepository.save(existingBook);
            return "Book borrowed successfully.";
        }
        return "Book does not exist.";
    }

    public String returnBook(Long id, Book updatedBook) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            existingBook.setBorrowed_books(existingBook.getBorrowed_books() - 1);
            existingBook.setBooks_left(existingBook.getBooks_left() + 1);

            if (existingBook.getBooks_left() > 0) {
                existingBook.setStatus(true);
            }else {
                existingBook.setStatus(false);
            }

            bookRepository.save(existingBook);
            return "Book returned successfully.";
        }
        return "Book does not exist.";
    }

    public String setStatus(Long id, Book setStatus) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            existingBook.setStatus(setStatus.getStatus());
            bookRepository.save(existingBook);
            return "Book status updated successfully.";
        }
        return "Book does not exist.";
    }

    public String deleteBook(Long id) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            bookRepository.deleteById(id);
            return "Book with ID " + id + " deleted successfully.";
        }
        return "Book does not exist.";
    }
}
