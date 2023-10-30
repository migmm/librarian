package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
