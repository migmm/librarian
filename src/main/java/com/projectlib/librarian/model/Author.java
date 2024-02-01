package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

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
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    @Schema(description = "Author ID")
    private Long id;

    @Column
    @Schema(description = "Author name")

    private String name;

    @Column
    @Schema(description = "Author surname")

    private String surname;

    @Column
    @Schema(description = "Author status")
    private  Boolean status;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    @Schema(description = "Author books")
    private Set<Book> books = new HashSet<>();
}
