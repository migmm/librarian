package com.projectlib.librarian.dto;

import com.projectlib.librarian.model.Vendor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    @Schema(description = "Book ID")
    private Long id;

    @NotNull(message = "ISBN is required.")
    @Digits(integer = 13, fraction = 0, message = "ISBN must contain exactly 13 digits.")
    @Positive(message = "ISBN must be a positive integer.")
    @Schema(description = "Book ISBN")
    private Long ISBN;

    @NotBlank(message = "Book title is required.")
    @Size(min = 2, max = 100, message = "Book title must be between 2 and 100 characters long.")
    @Schema(description = "Book title")
    private String title;

    @NotNull(message = "Book year is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Book year")
    private Date year;

    @NotNull(message = "Book quantity is required.")
    @Positive(message = "Book quantity must be a positive integer.")
    @Schema(description = "Book quantity")
    private Integer books_quantity;

    @Min(value = 0, message = "Borrowed books quantity must be zero or a positive integer.")
    @Schema(description = "Borrowed books quantity")
    private Integer borrowed_books;

    @Positive(message = "Book quantity must be a positive integer.")
    @Schema(description = "Book quantity left")
    private Integer books_left;

    @NotBlank(message = "Book genre is required.")
    @Size(min = 2, max = 100, message = "Book genre must be between 2 and 100 characters long.")
    @Schema(description = "Book genre")
    private String genre;

    @Schema(description = "Book status")
    private Boolean status;

    @Schema(description = "Book images")
    private List<String> images;

    @Schema(description = "Book vendor")
    private Vendor vendor;

    public Boolean getStatus() {
        return status;
    }
}