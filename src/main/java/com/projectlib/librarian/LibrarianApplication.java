package com.projectlib.librarian;

import com.projectlib.librarian.security.SecurityConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@SpringBootApplication()
@Import(SecurityConfig.class)
public class LibrarianApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibrarianApplication.class, args);
	}
}
