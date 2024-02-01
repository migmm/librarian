package com.projectlib.librarian.mapper;

import com.projectlib.librarian.dto.UserDTO;
import com.projectlib.librarian.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    private static ModelMapper modelMapper;

    public UserMapper() {
        this.modelMapper = new ModelMapper();
    }

    public static UserDTO convertToDTO(UserModel author) {
        return modelMapper.map(author, UserDTO.class);
    }

    public static UserModel convertToEntity(UserDTO authorDTO) {
        return modelMapper.map(authorDTO, UserModel.class);
    }
}
