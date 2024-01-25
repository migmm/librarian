package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.User_table;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User_table, Long> {
    Optional<User_table> findByUsername(String username);
}
