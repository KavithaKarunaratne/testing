package com.cwOOP.ticketingSystem.controller;

import com.cwOOP.ticketingSystem.dto.VendorDTO;
import com.cwOOP.ticketingSystem.dto.VendorDeleteDTO;
import com.cwOOP.ticketingSystem.dto.VendorLoginDTO;
import com.cwOOP.ticketingSystem.dto.VendorTicketAddDTO;
import com.cwOOP.ticketingSystem.service.TicketPoolService;
import com.cwOOP.ticketingSystem.service.VendorService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/api/vendor")
@CrossOrigin(origins = "http://localhost:5173")
public class VendorController {

    private final VendorService vendorService;


    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping("/create")
    public String createVendor (@RequestBody VendorDTO vendorDTO){
        return vendorService.createVendor(vendorDTO);
    }
    @PostMapping("/login")
    public String vendorLogin(@RequestBody VendorLoginDTO vendorLoginDTO){
        return vendorService.vendorLogin(vendorLoginDTO);
    }

    @DeleteMapping("/delete")
    public String vendorDelete(@RequestBody VendorDeleteDTO vendorDeleteDTO){
        return vendorService.vendorDelete(vendorDeleteDTO);
    }

    @PostMapping("/ticketAdd")
    public String vendorAddTicketsToTP(@RequestBody VendorTicketAddDTO vendorTicketAddDTO) {
        Callable<String> callable = () -> vendorService.vendorAddTicketsToTP(vendorTicketAddDTO);
        FutureTask<String> futureTask = new FutureTask<>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        try {
            // Wait for the thread to complete and retrieve the result
            return futureTask.get();

        } catch (InterruptedException | ExecutionException e) {
            // Handle exceptions if something goes wrong
            return "Error while processing the request." + e;
        }
    }

}



