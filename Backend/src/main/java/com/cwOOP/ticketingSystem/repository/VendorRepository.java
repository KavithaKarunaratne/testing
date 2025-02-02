package com.cwOOP.ticketingSystem.repository;


import com.cwOOP.ticketingSystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Vendor findVendorByEmail(String email);
}
