package com.projectlib.librarian.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.projectlib.librarian.dto.VendorDTO;
import com.projectlib.librarian.exception.NotFoundException;
import com.projectlib.librarian.model.Vendor;
import com.projectlib.librarian.repository.VendorRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class VendorServiceTest {

    @InjectMocks
    private VendorImplementation vendorService;

    @Mock
    private VendorRepository vendorRepository;

    @Test
    void findAllVendorsTest() {

        Vendor vendor1 = mock(Vendor.class);
        Vendor vendor2 = mock(Vendor.class);
        when(vendorRepository.findAll()).thenReturn(Arrays.asList(vendor1, vendor2));

        List<VendorDTO> result = vendorService.getAllVendors();

        assertEquals(2, result.size());
    }

    @Test
    void findAllITest() {

        Vendor vendor1 = new Vendor();
        vendor1.setId(1L);
        vendor1.setName("John");
        vendor1.setStatus(true);

        Vendor vendor2 = new Vendor();
        vendor2.setId(2L);
        vendor2.setName("Jane");
        vendor2.setStatus(true);

        List<Vendor> vendorList = Arrays.asList(vendor1, vendor2);
        when(vendorRepository.findAll()).thenReturn(vendorList);

        List<VendorDTO> expected = vendorList.stream()
                .map(vendor -> new VendorDTO(vendor.getId(), vendor.getName(), vendor.getStatus()))
                .collect(Collectors.toList());

        List<VendorDTO> result = vendorService.getAllVendors();

        assertNotNull(result);
        assertEquals(expected.size(), result.size());

        for (int i = 0; i < expected.size(); i++) {
            VendorDTO expectedDTO = expected.get(i);
            VendorDTO resultDTO = result.get(i);
            assertEquals(expectedDTO.getId(), resultDTO.getId());
            assertEquals(expectedDTO.getName(), resultDTO.getName());
            assertEquals(expectedDTO.getStatus(), resultDTO.getStatus());
        }
    }

    @Test
    void saveVendorTest() {

        Vendor vendor = mock(Vendor.class);
        VendorDTO vendorDto = mock(VendorDTO.class);
        when(vendorRepository.save(any(Vendor.class))).thenReturn(vendor);

        String result = vendorService.createVendor(vendorDto);

        assertEquals("Vendor created successfully.", result);
    }

    @Test
    void deleteVendorTest() {

        Long vendorId = 1L;
        doNothing().when(vendorRepository).deleteById(vendorId);

        String result = vendorService.deleteVendor(vendorId);

        assertEquals("Vendor with ID " + vendorId + " deleted successfully.", result);
    }

    @Test
    void findVendorByIdTest() {

        Long vendorId = 1L;
        Vendor vendor = new Vendor();
        vendor.setId(vendorId);
        when(vendorRepository.findById(vendorId)).thenReturn(Optional.of(vendor));

        VendorDTO result = vendorService.getVendorById(vendorId);

        assertEquals(vendorId, result.getId());
    }

    @Test
    void getVendorByIdNotFoundTest() {

        Long vendorId = 1L;
        when(vendorRepository.findById(vendorId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> vendorService.getVendorById(vendorId));
    }

    @Test
    void updateVendorNotFoundTest() {

        Long vendorId = 1L;
        VendorDTO updatedVendorDTO = new VendorDTO();
        updatedVendorDTO.setId(vendorId);

        assertThrows(NotFoundException.class, () -> vendorService.updateVendor(vendorId, updatedVendorDTO));
    }

    @Test
    void deleteVendorNotFoundTest() {

        Long vendorId = 1L;
        doThrow(new NotFoundException("Vendor with ID " + vendorId + " does not exist.")).when(vendorRepository)
                .deleteById(vendorId);

        assertThrows(NotFoundException.class, () -> vendorService.deleteVendor(vendorId));
    }
}