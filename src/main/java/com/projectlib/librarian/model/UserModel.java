package com.projectlib.librarian.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Schema(description = "User ID")
    private Long id;

    @Column
    @Schema(description = "User first name")
    private String name;

    @Column
    @Schema(description = "User surname")
    private String surname;

    @Column(unique = true)
    @Schema(description = "User email")
    private String email;

    @Column(unique = true)
    @Schema(description = "User username")
    private String username;

    @Column
    @Schema(description = "User role")
    private String role;

    @Column
    @Schema(description = "User password")
    private String password;

    @Column
    @Schema(description = "User status")
    private Boolean status;
}