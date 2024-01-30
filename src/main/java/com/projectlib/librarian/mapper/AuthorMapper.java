package com.projectlib.librarian.mapper;

import com.projectlib.librarian.dto.AuthorDTO;
import com.projectlib.librarian.model.Author;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    private static ModelMapper modelMapper;

    public AuthorMapper() {
        this.modelMapper = new ModelMapper();
    }

    public static AuthorDTO convertToDTO(Author author) {
        return modelMapper.map(author, AuthorDTO.class);
    }

    public static Author convertToEntity(AuthorDTO authorDTO) {
        return modelMapper.map(authorDTO, Author.class);
    }
}
