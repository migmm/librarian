package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User_table {
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

    @Column
    @Schema(description = "User email")
    private String email;

    @Column
    @Schema(description = "User username")
    private String username;

    @Column
    @Schema(description = "User role")
    private String role = "user";

    @Column
    @Schema(description = "User password")
    private String password;

    @Column
    @Schema(description = "User status")
    private Boolean status = true;
}