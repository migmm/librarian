package com.projectlib.librarian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    @Schema(description = "Vendor ID")
    private Long id;

    @Column
    @Schema(description = "Vendor name")
    @NotBlank(message = "Vendor name is required.")
    @Size(min = 2, max = 30, message = "Vendor name must be between 2 and 30 characters long.")
    private String name;

    @Column
    @Schema(description = "Vendor status")
    private Boolean status = true;

    @OneToMany(mappedBy = "vendor")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
}
