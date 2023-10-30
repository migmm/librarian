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

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id).orElse(null);
    }

    public void deleteVendor(Long id) {
        vendorRepository.deleteById(id);
    }

    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        Vendor existingVendor = getVendorById(id);
        if (existingVendor != null) {
            existingVendor.setName(updatedVendor.getName());
            existingVendor.setStatus(updatedVendor.getStatus());
            return vendorRepository.save(existingVendor);
        }
        return null;
    }
}
