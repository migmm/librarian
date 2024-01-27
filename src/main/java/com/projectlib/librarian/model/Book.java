package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @Schema(description = "Book ID")
    private Long id;

    @Column
    @Schema(description = "Book ISBN")
    @NotNull(message = "ISBN is required.")
    @Digits(integer = 13, fraction = 0, message = "ISBN must contain exactly 13 digits.")
    @Positive(message = "ISBN must be a positive integer.")
    private Long ISBN;

    @Column
    @Schema(description = "Book title")
    @NotBlank(message = "Book title is required.")
    @Size(min = 2, max = 100, message = "Book title must be between 2 and 100 characters long.")
    private String title;

    @Column
    @Schema(description = "Book year")
    @NotNull(message = "Book year is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date year;

    @Column
    @Schema(description = "Book quantity")
    @NotNull(message = "Book quantity is required.")
    @Positive(message = "Book quantity must be a positive integer.")
    private Integer books_quantity;

    @Column
    @Schema(description = "Borrowed books quantity")
    @Min(value = 0, message = "Borrowed books quantity must be zero or a positive integer.")
    private Integer borrowed_books = 0;

    @Column
    @Schema(description = "Book quantity left")
    @Positive(message = "Book quantity must be a positive integer.")
    private Integer books_left;

    @Column
    @NotBlank(message = "Book genre is required.")
    @Size(min = 2, max = 100, message = "Book genre must be between 2 and 100 characters long.") @Schema(description = "Book genre")
    private String genre;

    @Column
    @Schema(description = "Book status")
    private Boolean status = true;

    @ElementCollection
    @CollectionTable(name = "book_images", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "image_path")
    private List<String> images;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "vendor_id")
    @Schema(description = "Book vendor")
    private Vendor vendor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Schema(description = "Book author")
    private Set<Author> authors = new HashSet<>();

    public void setBooks_quantity(Integer books_quantity) {
        this.books_quantity = books_quantity;
        this.books_left = books_quantity;
    }
}
