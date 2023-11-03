package com.projectlib.librarian.controller;

import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.service.VendorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Vendor controllers tests")
public class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendorService vendorService;

    @Test
    @DisplayName("Get all vendors")
    public void testGetAllvendors() throws Exception {
        // Mock data
        List<Vendor> vendors = new ArrayList<>();

        vendors.add(new Vendor(1L, "Vendor 1",  true, new HashSet<>()));
        vendors.add(new Vendor(2L, "Vendor 2", true, new HashSet<>()));

        when(vendorService.getAllVendors()).thenReturn(vendors);

        mockMvc.perform(MockMvcRequestBuilders.get("/vendors/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Vendor 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Vendor 2"));

        verify(vendorService, times(1)).getAllVendors();
    }

    @Test
    @DisplayName("Get a vendor by ID")
    public void testGetvendorById() throws Exception {
        // Mock data
        Vendor vendor = new Vendor(1L, "Vendor 1", true, new HashSet<>());
        Long vendorId = 1L;

        when(vendorService.getVendorById(vendorId)).thenReturn(vendor);

        mockMvc.perform(MockMvcRequestBuilders.get("/vendors/find/{id}", vendorId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vendor 1"));

        verify(vendorService, times(1)).getVendorById(vendorId);
    }

    @Test
    @DisplayName("Create new vendor")
    public void testCreatevendor() throws Exception {
        // Mock data
        Vendor newvendor = new Vendor(1L, "Vendor 1", true, new HashSet<>());

        when(vendorService.createVendor(newvendor)).thenReturn("vendor created successfully.");

        mockMvc.perform(MockMvcRequestBuilders.post("/vendors/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New vendor\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"vendors_quantity\":5,"
                                + "\"borrowed_vendors\":0,"
                                + "\"vendors_left\":5,"
                                + "\"genre\":\"Adventure\","
                                + "\"status\":true,"
                                + "\"vendors\":["
                                + "]"
                                + "}"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Update a vendor")
    public void testUpdateVendor() throws Exception {
        // Mock data
        Long vendorId = 1L;
        Vendor updatedvendor = new Vendor(1L, "Vendor 1", true, new HashSet<>());

        when(vendorService.updateVendor(vendorId, updatedvendor)).thenReturn("vendor updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendors/update/{id}", vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"
                                + "\"id\":3,"
                                + "\"isbn\":0,"
                                + "\"title\":\"New vendor\","
                                + "\"year\":\"2023-11-02T21:02:24.982Z\","
                                + "\"vendors_quantity\":5,"
                                + "\"borrowed_vendors\":0,"
                                + "\"vendors_left\":5,"
                                + "\"genre\":\"Adventure\","
                                + "\"status\":true,"
                                + "\"vendors\":["
                                + "]"
                                + "}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Set vendor status (logical deletion)")
    public void testSetStatus() throws Exception {
        // Mock data
        Long vendorId = 1L;
        Vendor vendor = new Vendor(1L, "Vendor 1", true, new HashSet<>());

        when(vendorService.setStatus(vendorId, vendor)).thenReturn("vendor status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendors/setstatus/{id}", vendorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\":true}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete vendor")
    public void testDeletevendor() throws Exception {
        // Mock data
        Long vendorId = 1L;

        when(vendorService.deleteVendor(vendorId)).thenReturn("vendor with ID " + vendorId + " deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/vendors/delete/{id}", vendorId))
                .andExpect(status().isOk())
                .andExpect(content().string("vendor with ID " + vendorId + " deleted successfully."));

        verify(vendorService, times(1)).deleteVendor(vendorId);
    }
}
