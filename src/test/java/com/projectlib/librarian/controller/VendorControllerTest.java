package com.projectlib.librarian.controller;

import com.projectlib.librarian.dto.VendorDTO;

import com.projectlib.librarian.service.VendorImplementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Vendor controllers tests")
public class VendorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VendorImplementation vendorImplementation;

    @Value("${jwt.test.token}")
    private String JWT_TOKEN_TEST;


    @Test
    @DisplayName("Get all vendors")
    public void testGetAllVendorsTest() throws Exception {

        VendorDTO vendorDTO = mock(VendorDTO.class);
        VendorDTO vendorDTO2 = mock(VendorDTO.class);

        List<VendorDTO> vendorDTOList = new ArrayList<>();

        vendorDTOList.add(vendorDTO);
        vendorDTOList.add(vendorDTO2);

        when(vendorImplementation.getAllVendors()).thenReturn(vendorDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/vendors/findAll").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful()
                );
    }

    @Test
    @DisplayName("Get vendor by ID")
    public void testGetVendorByIdTest() throws Exception {

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(1L);
        vendorDTO.setName("Vendor name");

        when(vendorImplementation.getVendorById(1L)).thenReturn(vendorDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/vendors/find/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vendor name"));
    }

    @Test
    @DisplayName("Add new vendor")
    public void addNewVendorTest() throws Exception {

        VendorDTO vendorDTO = mock(VendorDTO.class);
        when(vendorImplementation.createVendor(vendorDTO)).thenReturn(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/vendors/save").with(csrf())
                        .content("{\"name\":\"Example name\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Edit a vendor")
    public void editVendorTest() throws Exception {

        VendorDTO updatedVendorDTO = new VendorDTO();

        updatedVendorDTO.setId(1L);
        updatedVendorDTO.setName("Updated Name");

        when(vendorImplementation.updateVendor(1L, updatedVendorDTO)).thenReturn("Vendor updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendors/update/1").with(csrf())
                        .content("{\"id\":1,\"name\":\"Updated Name\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Delete an vendor")
    public void deleteVendorTest() throws Exception {

        when(vendorImplementation.deleteVendor(1L)).thenReturn("Vendor with ID 1 deleted successfully.");

        mockMvc.perform(MockMvcRequestBuilders.delete("/vendors/delete/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Vendor with ID 1 deleted successfully."));
    }

    @Test
    @DisplayName("Set vendor status")
    public void setVendorStatusTest() throws Exception {
        Long vendorId = 1L;
        boolean status = false;

        when(vendorImplementation.setStatus(vendorId, status)).thenReturn("Vendor status updated successfully.");

        mockMvc.perform(MockMvcRequestBuilders.put("/vendors/setstatus/1").with(csrf())
                        .content("{\"status\":false}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + JWT_TOKEN_TEST))
                .andExpect(status().isOk());
    }
}
