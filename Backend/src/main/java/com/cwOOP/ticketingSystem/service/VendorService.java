package com.cwOOP.ticketingSystem.service;


import com.cwOOP.ticketingSystem.dto.VendorDTO;
import com.cwOOP.ticketingSystem.dto.VendorDeleteDTO;
import com.cwOOP.ticketingSystem.dto.VendorLoginDTO;
import com.cwOOP.ticketingSystem.dto.VendorTicketAddDTO;
import com.cwOOP.ticketingSystem.entity.Vendor;
import com.cwOOP.ticketingSystem.repository.VendorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VendorService {
    // Dependencies for repository and ticket pool service
    private final VendorRepository vendorRepository;
    private  final TicketPoolService ticketPoolService;
    // Constructor for dependency
    @Autowired
    public VendorService(VendorRepository vendorRepository, TicketPoolService ticketPoolService) {
        this.vendorRepository = vendorRepository;
        this.ticketPoolService=ticketPoolService;

    }


    public String createVendor(VendorDTO vendorDTO){
        Vendor vendor= vendorRepository.findVendorByEmail(vendorDTO.getEmail());
        if(vendor != null) {
            return "Given email by the vendor already exists.";
        }
        // Create and save a new vendor
        vendor=new Vendor();
        vendor.setPassword(vendorDTO.getPassword());
        vendor.setName(vendorDTO.getName());
        vendor.setEmail(vendorDTO.getEmail());
        vendor.setPhoneNumber(vendorDTO.getPhoneNumber());
        vendorRepository.save(vendor);
        log.info("A new vendor created.");
        return ("Vendor details saved.");
    }
    public String vendorLogin(VendorLoginDTO vendorLoginDTO) {
        Vendor vendor = vendorRepository.findVendorByEmail(vendorLoginDTO.getEmail());
        if (vendor != null && vendor.getPassword().equals(vendorLoginDTO.getPassword())) {
            log.info("A new vendor logged in.");
            return "vendor login successful.";
        } else if (vendor==null){
            return "Vendor not found.";
        }else {
            return "Invalid vendor inputs.";
        }
    }


    public String vendorDelete(VendorDeleteDTO vendorDeleteDTO){
        try {
            Vendor vendor = vendorRepository.findVendorByEmail(vendorDeleteDTO.getEmail());
            if (vendor != null && vendor.getPassword().equals(vendorDeleteDTO.getPassword())) {
                vendorRepository.delete(vendor);
                log.info("Vendor deleted.");
                return "Vendor deleted \nDeleted vendor - "+ vendorDeleteDTO.getEmail();
            } else if (vendor == null) {
                return "Vendor credentials can not be null!";
            } else {
                return "Invalid login!";
            }
        }catch (EntityNotFoundException e){
            log.error("Vendor not found to delete.", e);
            return "Vendor not found.";
        }catch (Exception e){
            log.error("An unexpected ERROR while vendor deletion!", e);
            return "ERROR!";
        }
    }
    public String vendorAddTicketsToTP(VendorTicketAddDTO vendorTicketAddDTO){
        try {
            String resultToVendor = ticketPoolService.addTicketsByVendor(vendorTicketAddDTO);
            log.info("Vendor added tickets to the pool.");
            return resultToVendor;
        }catch(Exception e){
            log.error("An ERROR occurred!", e);
            return "Vendor failed to add  all the tickets to the pool.";
        }
    }
}












