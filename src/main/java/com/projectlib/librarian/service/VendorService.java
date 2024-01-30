package com.projectlib.librarian.service;

import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Vendor with ID " + id + " does not exist."));
    }

    public String createVendor(Vendor vendor) {
        vendorRepository.save(vendor);
        return "Vendor created successfully.";
    }

    public String updateVendor(Long id, Vendor updatedVendor) {
        Vendor existingVendor = getVendorById(id);
        if (existingVendor == null) {
            throw new NotFoundException("Vendor with ID " + id + " does not exist.");
        }

        existingVendor.setName(updatedVendor.getName());
        existingVendor.setStatus(updatedVendor.getStatus());

        vendorRepository.save(existingVendor);

        return "Vendor updated successfully.";
    }


    public String setStatus(Long id, Boolean status) {
        Vendor existingVendor = getVendorById(id);
        if (existingVendor == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        existingVendor.setStatus(status);
        vendorRepository.save(existingVendor);
        return "Vendor status updated successfully.";
    }

    public String deleteVendor(Long id) {
        Vendor existingVendor = getVendorById(id);
        if (existingVendor == null) {
            throw new NotFoundException("User with ID " + id + " does not exist.");
        }

        vendorRepository.deleteById(id);
        return "Vendor with ID " + id + " deleted successfully.";
    }
}
