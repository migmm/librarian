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
    private Integer borrowed_books = 0;

    @Column
    @Schema(description = "Book quantity left")
    private Integer books_left;

    @Column
    @Schema(description = "Book genre")
    private String genre;

    @Column
    @Schema(description = "Book status")
    private Boolean status = true;

    @ElementCollection
    @CollectionTable(name = "book_images", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "image_path")
    @Schema(description = "Book images")
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
        if (this.books_left == null) {
            this.books_left = books_quantity;
        }
    }
}
