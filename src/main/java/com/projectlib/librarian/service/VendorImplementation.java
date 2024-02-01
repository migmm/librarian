package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.VendorDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.mapper.VendorMapper;
import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class VendorImplementation implements VendorInterface {
    
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public List<VendorDTO> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        return vendors.stream()
                .map(VendorMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor with ID " + id + " does not exist."));
        return VendorMapper.convertToDTO(vendor);
    }

    @Override
    public String createVendor(VendorDTO vendorDTO) {
        Vendor vendor = VendorMapper.convertToEntity(vendorDTO);
        vendorRepository.save(vendor);
        return "Vendor created successfully.";
    }

    @Override
    public String updateVendor(Long id, VendorDTO updatedVendorDTO) {
        Vendor existingVendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor with ID " + id + " does not exist."));

        Vendor updatedVendor = VendorMapper.convertToEntity(updatedVendorDTO);
        updatedVendor.setId(existingVendor.getId());

        vendorRepository.save(updatedVendor);
        return "Vendor updated successfully.";
    }

    @Override
    public String setStatus(Long id, Boolean status) {
        Vendor existingVendor = vendorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vendor with ID " + id + " does not exist."));

        existingVendor.setStatus(status);
        vendorRepository.save(existingVendor);
        return "Vendor status updated successfully.";
    }

    @Override
    public String deleteVendor(Long id) {
        vendorRepository.deleteById(id);
        return "Vendor with ID " + id + " deleted successfully.";
    }
}
