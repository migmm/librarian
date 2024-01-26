package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.User_table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository extends JpaRepository<User_table, Long> {
    User_table findByUsername(String username);
}
