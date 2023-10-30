package com.projectlib.librarian.service;

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

    public String createVendor(Vendor vendor) {
        vendorRepository.save(vendor);
        return "Vendor created successfully.";
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id).orElse(null);
    }

    public String deleteVendor(Long id) {
        vendorRepository.deleteById(id);
        return "Vendor with ID " + id + " deleted successfully.";
    }

    public String updateVendor(Long id, Vendor updatedVendor) {
        Vendor existingVendor = getVendorById(id);
        if (existingVendor != null) {
            existingVendor.setName(updatedVendor.getName());
            existingVendor.setStatus(updatedVendor.getStatus());
            return "Author updated successfully.";
        }
        return "Author does not exist.";
    }
}
