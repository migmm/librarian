package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.VendorDTO;
import com.projectlib.librarian.service.VendorInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Tag(name = "Vendors", description = "Endpoints to work with vendors.")
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorInterface vendorInterface;

    @GetMapping("/findAll")
    @Operation(summary = "Get all vendors", description = "Get a complete list of all vendors (does not include which have setStatus=false)")
    public ResponseEntity<List<VendorDTO>> getAllVendors() {
        List<VendorDTO> vendors = vendorInterface.getAllVendors();
        vendors.removeIf(vendor -> !vendor.getStatus());
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get an vendor by ID", description = "Get an vendor with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<VendorDTO> getVendorById(@PathVariable Long id) {
        VendorDTO vendor = vendorInterface.getVendorById(id);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createVendor(@Valid @RequestBody VendorDTO vendorDTO) {
        String message = vendorInterface.createVendor(vendorDTO);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update an vendor", description = "Update an vendor information using the ID as param.")
    public ResponseEntity<String> updateVendor(@Valid @PathVariable Long id, @Valid @RequestBody VendorDTO vendorDTO) {
        String message = vendorInterface.updateVendor(id, vendorDTO);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")
    @Operation(summary = "Set vendor status", description = "Set vendor status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> requestBody) {
        Boolean status = requestBody.get("status");
        if (status == null) {
            return new ResponseEntity<>("Invalid status value. Status must be either true or false.", HttpStatus.BAD_REQUEST);
        }

        String message = vendorInterface.setStatus(id, status);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an vendor", description = "Delete an vendor using the ID as param.")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        String message = vendorInterface.deleteVendor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}