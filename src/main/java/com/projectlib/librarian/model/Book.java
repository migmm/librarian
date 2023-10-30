package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long id;

    @Column
    private Long ISBN;

    @Column
    private String title;

    @Column
    private Date year;

    @Column
    private Integer books_quantity;

    @Column
    private Integer borrowed_books;

    @Column
    private Boolean status;

    @Column
    private String genre;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();
}
