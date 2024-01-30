package com.projectlib.librarian.service;

import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class VendorServiceTest {

    @Mock
    private VendorRepository vendorRepository;

    @InjectMocks
    private VendorImplementation vendorImplementation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor(1L, "Vendor 1", true, new HashSet<>()));

        when(vendorRepository.findAll()).thenReturn(vendors);

        List<Vendor> result = vendorImplementation.getAllVendors();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetVendorById() {
        Long vendorId = 1L;
        Vendor vendor = new Vendor(vendorId, "Vendor 1", true, new HashSet<>());

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));

        Vendor result = vendorImplementation.getVendorById(vendorId);

        assertEquals(vendor, result);
    }

    @Test
    public void testGetVendorByIdVendorNotFound() {
        Long vendorId = 1L;

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.empty());

        Vendor result = vendorImplementation.getVendorById(vendorId);

        assertNull(result);
    }

    @Test
    public void testCreateVendor() {
        Vendor newVendor = new Vendor(1L, "New Vendor", true, new HashSet<>());

        vendorImplementation.createVendor(newVendor);

        Mockito.verify(vendorRepository, Mockito.times(1)).save(newVendor);
    }

    @Test
    public void testUpdateVendor() {
        Long vendorId = 1L;
        Vendor existingVendor = new Vendor(vendorId, "Existing", true, new HashSet<>());
        Vendor updatedVendor = new Vendor(vendorId, "Updated Vendor", true, new HashSet<>());

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(existingVendor));
        vendorImplementation.updateVendor(vendorId, updatedVendor);

        String message = vendorImplementation.updateVendor(vendorId, updatedVendor);

        assertEquals("Vendor updated successfully.", message);
    }

    @Test
    public void testUpdateVendorVendorNotFound() {
        Long vendorId = 1L;
        Vendor updatedVendor = new Vendor(vendorId, "Updated Vendor", false, new HashSet<>());

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.empty());
        vendorImplementation.updateVendor(vendorId, updatedVendor);

        Mockito.verify(vendorRepository, Mockito.times(0)).save(updatedVendor);
    }

    @Test
    public void testSetStatus() {
        Long vendorId = 1L;
        Vendor existingVendor = new Vendor(vendorId, "Vendor 1", true, new HashSet<>());
        Vendor updatedVendor = new Vendor(vendorId, "Vendor 1", false, new HashSet<>());

        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(existingVendor));
        String message = vendorImplementation.setStatus(vendorId, updatedVendor);

        assertEquals("Vendor status updated successfully.", message);
    }

    @Test
    public void testDeleteVendor() {
        Long vendorId = 1L;
        vendorImplementation.deleteVendor(vendorId);

        Mockito.verify(vendorRepository, Mockito.times(1)).deleteById(vendorId);
    }
}
