package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    @Schema(description = "Author ID")
    private Long id;

    @Column
    @Schema(description = "Author name")
    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private String name;

    @Column
    @Schema(description = "Author surname")
    @NotBlank(message = "Surname is required.")
    @Size(max = 100, message = "Surname cannot exceed 100 characters.")
    private String surname;

    @Column
    @Schema(description = "Author status")
    @NotNull(message = "Status is required.")
    private  Boolean status;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    @Schema(description = "Author books")
    private Set<Book> books = new HashSet<>();
}
