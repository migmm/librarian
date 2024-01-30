package com.projectlib.librarian.mapper;

import com.projectlib.librarian.dto.VendorDTO;
import com.projectlib.librarian.model.Vendor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VendorMapper {

    private static ModelMapper modelMapper;

    public VendorMapper() {
        this.modelMapper = new ModelMapper();
    }

    public static VendorDTO convertToDTO(Vendor author) {
        return modelMapper.map(author, VendorDTO.class);
    }

    public static Vendor convertToEntity(VendorDTO authorDTO) {
        return modelMapper.map(authorDTO, Vendor.class);
    }
}
