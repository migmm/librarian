package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserModel, Long> {

}
