package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.User_table;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User_table, Long> {

}
