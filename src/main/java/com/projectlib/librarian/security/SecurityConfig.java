package com.projectlib.librarian.security;

import com.projectlib.librarian.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import com.projectlib.librarian.jwt.JwtTokenUtil;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private AuthenticationManager authenticationManager;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize

                        // Public URLs
                        .requestMatchers("swagger-ui/**", "v3/api-docs/**").permitAll()
                        .requestMatchers("/", "/auth/login", "/auth/register").permitAll()
                        .requestMatchers("/books/findAll", "/books/find/**").permitAll()
                        .requestMatchers( "/authors/findAll", "/authors/find/**").permitAll()
                        .requestMatchers("/vendors/findAll", "/vendors/find/**").permitAll()
                        .requestMatchers( "/users/save", "/users/update/**", "/users/setstatus/**").permitAll()

                        // Authenticated URLs
                        // USER or ADMIN
                        .requestMatchers("/auth/logout", "/auth/refresh-token").authenticated()
                        .requestMatchers("/books/borrow/**", "/books/return/**").authenticated()

                        // ONLY ADMIN
                        .requestMatchers("/users/findAll", "/users/find/**", "/users/delete/**").hasAuthority("admin")
                        .requestMatchers("/vendors/save", "/vendors/update/**", "/vendors/delete/**", "/vendors/setstatus/**").hasAuthority("admin")
                        .requestMatchers("/authors/save", "/authors/update/**", "/authors/delete/**", "/authors/setstatus/**").hasAuthority("admin")
                        .requestMatchers("/books/save", "/books/update/**", "/books/delete/**", "/books/setstatus/**").hasAuthority("admin"))

                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)

                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                );

                // enable this for testing routes
                //.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
