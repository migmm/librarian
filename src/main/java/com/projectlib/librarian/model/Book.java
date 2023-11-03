package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
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
    private Long ISBN;

    @Column
    @Schema(description = "Book title")
    private String title;

    @Column
    @Schema(description = "Book year")
    private Date year;

    @Column
    @Schema(description = "Book quantity")
    private Integer books_quantity;

    @Column
    @Schema(description = "Borrowed books quantity")
    private Integer borrowed_books;

    @Column
    @Schema(description = "Book quantity left")
    private Integer books_left;

    @Column
    @Schema(description = "Book genre")
    private String genre;

    @Column
    @Schema(description = "Book status")
    private Boolean status;

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
}
