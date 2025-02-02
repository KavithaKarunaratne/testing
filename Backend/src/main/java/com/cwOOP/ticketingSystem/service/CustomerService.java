package com.cwOOP.ticketingSystem.service;


import com.cwOOP.ticketingSystem.dto.CustomerDTO;
import com.cwOOP.ticketingSystem.dto.CustomerDeleteDTO;
import com.cwOOP.ticketingSystem.dto.CustomerLoginDTO;
import com.cwOOP.ticketingSystem.dto.CustomerTicketsPurchaseDTO;
import com.cwOOP.ticketingSystem.entity.Customer;
import com.cwOOP.ticketingSystem.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j //lombok annotation for logging

public class CustomerService {

    // Dependencies for repository and ticket pool
    private final CustomerRepository customerRepository;
    private final TicketPoolService ticketPoolService;
    //constructor for dependency
    @Autowired
    public CustomerService(CustomerRepository customerRepository, TicketPoolService ticketPoolService) {
        this.customerRepository = customerRepository;
        this.ticketPoolService=ticketPoolService;
    }

    public String createCustomer(CustomerDTO customerDTO) {
        try {
            // Check if the email already exists in the system
            Customer customer1 = customerRepository.findCustomerByEmail(customerDTO.getEmail());
            if(customer1 != null) {
                return "Given email by the customer already exists.";
            }
            //create and save the new customer
            Customer customer = new Customer();
            customer.setPassword(customerDTO.getPassword());
            customer.setName(customerDTO.getName());
            customer.setEmail(customerDTO.getEmail());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customerRepository.save(customer);
            log.info("Customer profile created");
            return "Customer details saved";
        } catch (Exception e){
            return "An error occurred." + e;
        }
    }

    public String customerLogin (CustomerLoginDTO customerLoginDTO){
        // Retrieve the customer using email
        Customer customer = customerRepository.findCustomerByEmail(customerLoginDTO.getEmail());
        if (customer!=null && customer.getPassword().equals(customerLoginDTO.getPassword())){
            log.info("A new customer logged in");
            return "Login successful";
        }else if (customer==null){
            return "Customer not found";
        }else {
            return "Invalid customer inputs";
        }
    }

    public String customerDelete (CustomerDeleteDTO customerDeleteDTO){
        try{
            // Delete the customer if credentials match
            Customer customer = customerRepository.findCustomerByEmail(customerDeleteDTO.getEmail());
            if(customer != null && customer.getPassword().equals(customerDeleteDTO.getPassword())){
                customerRepository.delete(customer);
                log.info("Customer deleted");
                return "Customer deleted" + customerDeleteDTO.getEmail();
             } else if (customer==null) {
                return "Customer is not found to delete -" + customerDeleteDTO.getEmail();
            }else {
                return "Invalid login";
            }
        }catch (EntityNotFoundException e){
        log.error("Customer not found to delete", e);
        return "Customer not found";
        }catch (Exception e){
        log.error("An unexpected error while customer deletion", e);
        return "ERROR!";
        }
    }
    public String customerPurchaseTicketsFromTP(CustomerTicketsPurchaseDTO customerTicketsPurchaseDTO){
        try{
            // Transfer ticket purchase logic to the TicketPoolService
            String resultToVendor=ticketPoolService.purchaseTicketsByCustomer(customerTicketsPurchaseDTO);
            log.info(resultToVendor);
            return resultToVendor;

        }catch(Exception e){
            log.error("An error occurred", e);
            return "Customer failed to purchase a ticket from the ticket pool";
        }
    }
}