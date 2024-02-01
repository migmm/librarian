package com.projectlib.librarian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    @Schema(description = "Author ID")
    private Long id;

    @Schema(description = "Author name")
    @NotBlank(message = "Name is required.")
    @Size(min = 2, message = "Name must be at least 2 characters long.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String name;

    @Schema(description = "Author surname")
    @NotBlank(message = "Surname is required.")
    @Size(min = 2, message = "Surname must be at least 2 characters long.")
    @Size(max = 100, message = "Surname cannot exceed 100 characters.")
    private String surname;

    @Schema(description = "Author status")
    private Boolean status = true;

    public Boolean getStatus() {
        return status;
    }
}
