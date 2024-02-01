package com.projectlib.librarian.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

    @Schema(description = "Vendor ID")
    private Long id;

    @NotBlank(message = "Vendor name is required.")
    @Size(min = 2, max = 30, message = "Vendor name must be between 2 and 30 characters long.")
    @Schema(description = "Vendor name")
    private String name;

    @Schema(description = "Vendor status")
    private Boolean status = true;

    public Boolean getStatus() {
        return status;
    }
}