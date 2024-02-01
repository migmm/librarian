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
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    @Schema(description = "Vendor ID")
    private Long id;

    @Column
    @Schema(description = "Vendor name")
    private String name;

    @Column
    @Schema(description = "Vendor status")
    private Boolean status;

    @OneToMany(mappedBy = "vendor")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();
}
