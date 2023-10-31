package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VendorControllerTest {

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor(1L, "Vendor 1", true, new HashSet<>()));

        when(vendorService.getAllVendors()).thenReturn(vendors);

        ResponseEntity<List<Vendor>> response = vendorController.getAllVendors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetVendorById() {
        Long vendorId = 1L;
        Vendor vendor = new Vendor(vendorId, "Vendor 1", true, new HashSet<>());

        when(vendorService.getVendorById(vendorId)).thenReturn(vendor);

        ResponseEntity<Vendor> response = vendorController.getVendorById(vendorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vendorId, response.getBody().getId());
    }


    @Test
    public void testCreateVendor() {
        Vendor newVendor = new Vendor(1L, "New Vendor", true, new HashSet<>());

        when(vendorService.createVendor(Mockito.any(Vendor.class))).thenReturn("Vendor created successfully.");

        ResponseEntity<String> response = vendorController.createVendor(newVendor);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Vendor created successfully.", response.getBody());
    }

    @Test
    public void testUpdateVendor() {
        Long vendorId = 1L;
        Vendor updatedVendor = new Vendor(vendorId, "Updated Vendor", false, new HashSet<>());

        when(vendorService.updateVendor(vendorId, updatedVendor)).thenReturn("Vendor updated successfully.");

        ResponseEntity<String> response = vendorController.updateVendor(vendorId, updatedVendor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor updated successfully.", response.getBody());
    }

    @Test
    public void testSetStatus() {
        Long vendorId = 1L;
        Vendor updatedVendor = new Vendor(vendorId, "Vendor 1", false, new HashSet<>());

        when(vendorService.setStatus(vendorId, updatedVendor)).thenReturn("Vendor status updated successfully.");

        ResponseEntity<String> response = vendorController.setStatus(vendorId, updatedVendor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor status updated successfully.", response.getBody());
    }

    @Test
    public void testDeleteVendor() {
        Long vendorId = 1L;

        when(vendorService.deleteVendor(vendorId)).thenReturn("Vendor with ID 1 deleted successfully.");

        ResponseEntity<String> response = vendorController.deleteVendor(vendorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor with ID 1 deleted successfully.", response.getBody());
    }

    @Test
    public void testDeleteVendorVendorDoesNotExist() {
        Long vendorId = 1L;

        when(vendorService.deleteVendor(vendorId)).thenReturn("Vendor does not exist.");

        ResponseEntity<String> response = vendorController.deleteVendor(vendorId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vendor does not exist.", response.getBody());
    }
}
