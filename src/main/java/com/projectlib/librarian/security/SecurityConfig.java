package com.projectlib.librarian.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/books/findAll", "/authors/findAll").permitAll()
                        .requestMatchers("/admin").hasAuthority("ADMIN")
                        .requestMatchers("/customer").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/alluser").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated());
        return http.build();
    }
}
