package com.projectlib.librarian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @Schema(description = "User first name")
    @NotBlank(message = "User first name is required.")
    @Size(min = 2, max = 30, message = "User first name must be between 2 and 30 characters long.")
    private String name;

    @Schema(description = "User surname")
    @NotBlank(message = "User surname is required.")
    @Size(min = 2, max = 30, message = "User surname must be between 2 and 30 characters long.")
    private String surname;

    @Schema(description = "User email")
    @NotBlank(message = "User email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @Schema(description = "User username")
    @NotBlank(message = "User username is required.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username must contain only letters.")
    @Size(min = 4, max = 8, message = "Username must be at least 8 characters long and 16 maximum.")
    private String username;

    @Schema(description = "User role")
    private String role;

    @Schema(description = "User password")
    @NotBlank(message = "User password is required.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,16}$", message = "Password must contain both letters and numbers and have a length between 8 and 16 characters.")
    private String password;

    @Schema(description = "User status")
    private  Boolean status;
}
