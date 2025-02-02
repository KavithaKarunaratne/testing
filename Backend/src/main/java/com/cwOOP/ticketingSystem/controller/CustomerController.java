package com.cwOOP.ticketingSystem.controller;

import com.cwOOP.ticketingSystem.dto.CustomerDTO;
import com.cwOOP.ticketingSystem.dto.CustomerDeleteDTO;
import com.cwOOP.ticketingSystem.dto.CustomerLoginDTO;
import com.cwOOP.ticketingSystem.dto.CustomerTicketsPurchaseDTO;
import com.cwOOP.ticketingSystem.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;



    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;

    }


    @PostMapping("/create")
    public String createCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.createCustomer(customerDTO);
    }
    @PostMapping ("/login")
    public String customerLogin(@RequestBody CustomerLoginDTO customerLoginDTO){
        return customerService.customerLogin(customerLoginDTO);
    }
    @DeleteMapping("/delete")
    public String customerDelete(@RequestBody CustomerDeleteDTO customerDeleteDTO){
        return customerService.customerDelete(customerDeleteDTO);
    }
    @PostMapping("/ticketPurchase")
    public String customerPurchaseTicketsFromTP(@RequestBody CustomerTicketsPurchaseDTO customerTicketsPurchaseDTO){
        Callable<String> callable= () -> customerService.customerPurchaseTicketsFromTP(customerTicketsPurchaseDTO);
        FutureTask<String >futureTask=new FutureTask<>(callable);
        Thread thread=new Thread(futureTask);
        thread.start();
        try {
            return futureTask.get();
        }catch(InterruptedException| ExecutionException e){
            return "Error while processing the request." + e;
        }
    }
}
