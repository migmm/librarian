package com.projectlib.librarian.service;


import com.projectlib.librarian.dto.BookDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.mapper.BookMapper;
import com.projectlib.librarian.model.Book;

import com.projectlib.librarian.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.projectlib.librarian.helper.FilesHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookImplementation implements BookInterface{
    
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private FilesHelper filesHelper;

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(BookMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with ID " + id + " does not exist."));
        return BookMapper.convertToDTO(book);
    }

    @Override
    public String createBook(BookDTO bookDTO, List<MultipartFile> imagePaths) throws IOException {
        List<String> savedImagePaths = new ArrayList<>();

        for (MultipartFile multipartFile : imagePaths) {
            String savedImagePath = filesHelper.saveImageToServer(multipartFile);
            savedImagePaths.add(savedImagePath);
        }

        bookDTO.setImages(savedImagePaths);
        Book bookEntity = BookMapper.convertToEntity(bookDTO);
        bookRepository.save(bookEntity);

        return "Book created successfully.";
    }

    @Override
    public String updateBook(Long id, BookDTO updatedBook, List<MultipartFile> newImages) throws IOException {
        Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book with ID " + id + " does not exist."));

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
                String savedImagePath = filesHelper.saveImageToServer(multipartFile);
                savedImagePaths.add(savedImagePath);
            }
            existingBook.setImages(savedImagePaths);
        }

        bookRepository.save(existingBook);
        return "Book updated successfully.";
    }

    @Override
    public String borrowBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with ID " + id + " does not exist."));

        if (existingBook.getBooks_left() <= 0) {
            return "There is no more books to take.";
        }

        existingBook.setBorrowed_books(existingBook.getBorrowed_books() + 1);
        existingBook.setBooks_left(existingBook.getBooks_quantity() - existingBook.getBorrowed_books());

        if (existingBook.getBooks_left() == 0) {
            existingBook.setStatus(false);
        }

        bookRepository.save(existingBook);
        return "Book borrowed successfully.";
    }

    @Override
    public String returnBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with ID " + id + " does not exist."));

        if (existingBook.getBorrowed_books() <= 0) {
            return "There are no borrowed books to return.";
        }

        existingBook.setBorrowed_books(existingBook.getBorrowed_books() - 1);
        existingBook.setBooks_left(existingBook.getBooks_left() + 1);

        if (existingBook.getStatus() == false) {
            existingBook.setStatus(true);
        }

        bookRepository.save(existingBook);
        return "Book returned successfully.";
    }

    @Override
    public String setStatus(Long id, Boolean status) {
        Book existingBook = bookRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Book with ID " + id + " does not exist."));

        existingBook.setStatus(status);
        bookRepository.save(existingBook);
        return "Book status updated successfully.";
    }

    @Override
    public String deleteBook(Long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with ID " + id + " does not exist."));

        bookRepository.deleteById(id);
        return "Book with ID " + id + " deleted successfully.";
    }
}