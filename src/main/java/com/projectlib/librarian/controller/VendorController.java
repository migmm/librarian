package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Vendors", description = "Endpoints to work with vendors.")
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @GetMapping("/findAll")
    @Operation(summary = "Get all vendors", description = "Get a complete list of all vendors (does not include which have setStatus=false)")
    public ResponseEntity<List<Vendor>> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        vendors.removeIf(vendor -> !vendor.getStatus());
        return new ResponseEntity<>(vendors, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Get a vendor by ID", description = "Get a vendor with full information by ID (does not include which have setStatus=false)")
    public ResponseEntity<Vendor>  getVendorById(@PathVariable Long id) {
        Vendor vendor = vendorService.getVendorById(id);
        return new ResponseEntity<>(vendor, HttpStatus.OK);
    }

    @PostMapping("/save")
    @Operation(summary = "Save a new vendor", description = "Save a new vendor with full information using the ID as param.")
    public ResponseEntity<String> createVendor(@Valid @RequestBody Vendor vendor) {
        String message = vendorService.createVendor(vendor);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a vendor", description = "Update a vendor information using the ID as param.")
    public ResponseEntity<String> updateVendor(@Valid @PathVariable Long id, @RequestBody Vendor vendor) {
        String message = vendorService.updateVendor(id, vendor);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PutMapping("/setstatus/{id}")  
    @Operation(summary = "Set vendor status", description = "Set vendor status using the ID as param. It used as logical deletion, possible options: true or false.")
    public ResponseEntity<String> setStatus(@PathVariable Long id, @RequestBody Vendor vendor) {
        String message = vendorService.setStatus(id, vendor);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a vendor", description = "Delete a vendor using the ID as param.")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        String message = vendorService.deleteVendor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}