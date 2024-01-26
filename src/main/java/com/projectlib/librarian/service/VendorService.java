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
        if (existingVendor != null) {
            existingVendor.setName(updatedVendor.getName());
            existingVendor.setStatus(updatedVendor.getStatus());
            return "Vendor updated successfully.";
        }
        return "Vendor does not exist.";
    }

    public String setStatus(Long id, Vendor setStatus) {
        Vendor existingVendor = getVendorById(id);
        if (existingVendor != null) {
            existingVendor.setStatus(setStatus.getStatus());
            return "Vendor status updated successfully.";
        }
        return "Vendor does not exist.";
    }

    public String deleteVendor(Long id) {
        vendorRepository.deleteById(id);
        return "Vendor with ID " + id + " deleted successfully.";
    }
}
