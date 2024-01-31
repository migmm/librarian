package com.projectlib.librarian.mapper;

import com.projectlib.librarian.dto.BookDTO;
import com.projectlib.librarian.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private static ModelMapper modelMapper;

    public BookMapper() {
        this.modelMapper = new ModelMapper();
    }

    public static BookDTO convertToDTO(Book author) {
        return modelMapper.map(author, BookDTO.class);
    }

    public static Book convertToEntity(BookDTO authorDTO) {
        return modelMapper.map(authorDTO, Book.class);
    }
}
