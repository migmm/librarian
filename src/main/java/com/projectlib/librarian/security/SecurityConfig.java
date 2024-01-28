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
                        .requestMatchers("/", "/auth/login", "/auth/refresh-token", "/auth/register", "/auth/logout").permitAll()
                        // Books URLs
                        .requestMatchers("/books/find/**", "/books/save", "/books/update/**", "/books/delete/**", "/books/setstatus/**", "/books/borrow/**", "/books/return/**").permitAll()
                        // Authors URLs
                        .requestMatchers( "/authors/findAll", "/authors/find/**", "/authors/save", "/authors/update/**", "/authors/delete/**", "/authors/setstatus/**").permitAll()
                        // Vendors URLs
                        .requestMatchers("/vendors/findAll", "/vendors/find/**", "/vendors/save", "/vendors/update/**", "/vendors/delete/**", "/vendors/setstatus/**").permitAll()
                        // Users URLs
                        .requestMatchers("/users/findAll", "/users/find/**", "/users/save", "/users/update/**", "/users/delete/**", "/users/setstatus/**").permitAll()
                        // Authenticated URLs
                        //.requestMatchers("/admin").hasAuthority("ADMIN")
                        //.requestMatchers("/customer").hasAnyRole("ADMIN","USER")
                        //.requestMatchers("/alluser").hasAnyAuthority("ADMIN", "USER").anyRequest().authenticated()
                        .requestMatchers("/books/findAll").authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(authenticationManager, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
                //.csrf((csrf) -> csrf
                //        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                //);

                // Temporarly disable csrf for testing
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
