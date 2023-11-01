package com.projectlib.librarian.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.service.VendorService;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.HashSet;


@SpringBootTest
@AutoConfigureMockMvc
public class VendorControllerTest {

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllVendors() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/vendors/findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.size()", greaterThan(2)));
    }

    @Test
    public void testGetVendorById() throws Exception {
        Long vendorId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.get("/vendors/find/{id}", vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(vendorId));
    }

    @Test
    public void testCreateVendor() throws Exception {
        Vendor newVendor = new Vendor(1L, "New Vendor", true, new HashSet<>());

        when(vendorService.createVendor(Mockito.any(Vendor.class))).thenReturn("Vendor created successfully.");

        mockMvc.perform(MockMvcRequestBuilders.post("/vendors/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVendor)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Vendor created successfully."));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        Long vendorId = 1L;
        Vendor updatedVendor = new Vendor(vendorId, "Updated Vendor", false, new HashSet<>());

        when(vendorService.updateVendor(vendorId, updatedVendor)).thenReturn("Vendor updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendors/update/{id}", vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVendor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Vendor updated successfully."));
    }

    @Test
    public void testSetStatus() throws Exception {
        Long vendorId = 1L;
        Vendor updatedVendor = new Vendor(vendorId, "Vendor 1", false, new HashSet<>());

        when(vendorService.setStatus(vendorId, updatedVendor)).thenReturn("Vendor status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendors/setstatus/{id}", vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedVendor)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Vendor status updated successfully."));
    }

    @Test
    @Transactional
    public void testDeleteVendor() throws Exception {
        Long vendorId = 2L;

        when(vendorService.deleteVendor(vendorId)).thenReturn("Vendor with ID " + vendorId + " deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/vendors/delete/{id}", vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Vendor with ID " + vendorId + " deleted successfully."));
    }
}
