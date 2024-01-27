package com.projectlib.librarian.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Public URLs
                        .requestMatchers("/", "/auth/login", "/auth/register", "/auth/logout").permitAll()
                        // Books URLs
                        .requestMatchers("/books/findAll", "/books/find/**", "/books/save", "/books/update/**", "/books/delete/**", "/books/setstatus/**", "/books/borrow/**", "/books/return/**").permitAll()
                        // Authors URLs
                        .requestMatchers("/authors/findAll", "/authors/find/**", "/authors/save", "/authors/update/**", "/authors/delete/**", "/authors/setstatus/**").permitAll()
                        // Vendors URLs
                        .requestMatchers("/vendors/findAll", "/vendors/find/**", "/vendors/save", "/vendors/update/**", "/vendors/delete/**", "/vendors/setstatus/**").permitAll()
                        // Users URLs
                        .requestMatchers("/users/findAll", "/users/find/**", "/users/save", "/users/update/**", "/users/delete/**", "/users/setStatus/**").permitAll()
                        // Authenticated URLs
                        .requestMatchers("/admin").hasAuthority("ADMIN")
                        .requestMatchers("/customer").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/alluser").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated())
                        .csrf((csrf) -> csrf
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        );
                        
        return http.build();
    }
}
