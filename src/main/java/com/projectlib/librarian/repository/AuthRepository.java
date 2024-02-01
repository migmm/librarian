package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthRepository extends JpaRepository<UserModel, Long> {
    
    UserModel findByUsername(String username);
}
