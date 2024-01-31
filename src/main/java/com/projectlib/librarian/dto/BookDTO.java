package com.projectlib.librarian.dto;

import com.projectlib.librarian.model.Vendor;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private Long ISBN;
    private String title;
    private Date year;
    private Integer books_quantity;
    private Integer borrowed_books;
    private Integer books_left;
    private String genre;
    private Boolean status;
    private List<String> images;
    private Vendor vendor;

    public Boolean getStatus() {
        return status;
    }
}