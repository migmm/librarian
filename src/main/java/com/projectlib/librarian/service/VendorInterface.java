package com.projectlib.librarian.service;

import com.projectlib.librarian.dto.VendorDTO;
import java.util.List;


public interface VendorInterface {
    
    List<VendorDTO> getAllVendors();
    VendorDTO getVendorById(Long id);
    String createVendor(VendorDTO vendorDTO);
    String updateVendor(Long id, VendorDTO updatedVendorDTO);
    String setStatus(Long id, Boolean status);
    String deleteVendor(Long id);
}
