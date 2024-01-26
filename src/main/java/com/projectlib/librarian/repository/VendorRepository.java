package com.projectlib.librarian.repository;

import com.projectlib.librarian.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
