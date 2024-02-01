package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthorRepository extends JpaRepository<Author, Long> {
}
