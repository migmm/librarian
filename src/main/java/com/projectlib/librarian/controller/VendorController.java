package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/vendors")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @GetMapping("/findAll")
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/find/{id}")
    public Vendor getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<String> createVendor(@RequestBody Vendor vendor) {
        String message = vendorService.createVendor(vendor);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        String message = vendorService.updateVendor(id, vendor);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
        String message = vendorService.deleteVendor(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}