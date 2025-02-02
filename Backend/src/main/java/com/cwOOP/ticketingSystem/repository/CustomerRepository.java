package com.cwOOP.ticketingSystem.repository;


import com.cwOOP.ticketingSystem.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findCustomerByEmail(String email);
}
