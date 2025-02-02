package com.cwOOP.ticketingSystem.service;

import com.cwOOP.ticketingSystem.dto.CustomerTicketsPurchaseDTO;
import com.cwOOP.ticketingSystem.dto.VendorTicketAddDTO;
import com.cwOOP.ticketingSystem.entity.Event;
import com.cwOOP.ticketingSystem.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class TicketPoolService {
    private final EventRepository eventRepository;
    private EventService eventService;
    private final ReentrantLock vendorLock = new ReentrantLock(); // fair lock
    private final ReentrantLock customerLock = new ReentrantLock();

    @Autowired
    public TicketPoolService(EventRepository eventRepository, EventService eventService) {
        this.eventRepository = eventRepository;
        this.eventService = eventService;
    }



    // Simulate variable processing time
    private void simulateVariableDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted during delay", e);
        }
    }

    public String addTicketsByVendor(VendorTicketAddDTO vendorTicketAddDTO) {
        vendorLock.lock();
        try {
            Event event1 = eventRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            int ticketsToAdd = Math.min(
                    vendorTicketAddDTO.getTicketsVendorHave(),
                    event1.getMaxTicketCapacity() - event1.getCurrentTicketsInTP()
            );

            int addedTickets = 0;
            while (addedTickets < ticketsToAdd && event1.getCurrentTicketsInTP() < event1.getMaxTicketCapacity()) {
                // Simulate variable processing time
                simulateVariableDelay();
                Event event = eventRepository.findById(1L).orElseThrow(() -> new RuntimeException("Event not found"));


                // Add one ticket
                event.setCurrentTicketsInTP(event.getCurrentTicketsInTP() + 1);

                eventRepository.save(event);
                eventService.notifyEmitters();

                addedTickets++;
                log.info("Vendor added 1 ticket. Current pool: {}", event.getCurrentTicketsInTP());

            }

            log.info("Vendor added {} tickets", addedTickets);
            return "Vendor added " + addedTickets + " tickets";
        } catch (Exception e) {
            log.error("Unexpected error while adding tickets", e);
            return "Unexpected error while adding tickets " + e;
        } finally {
            vendorLock.unlock();
        }
    }

    public String purchaseTicketsByCustomer(CustomerTicketsPurchaseDTO customerTicketsPurchaseDTO) {
        customerLock.lock();
        try {
            Event event1 = eventRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            int ticketsToPurchase = customerTicketsPurchaseDTO.getTicketsCustomerWant();
            int ticketsPurchased = 0;

            while (ticketsPurchased < ticketsToPurchase && event1.getCurrentTicketsInTP() > 0) {
                // Simulate variable processing time
                simulateVariableDelay();
                Event event = eventRepository.findById(1L).orElseThrow(() -> new RuntimeException("Event not found"));

                event.setCurrentTicketsInTP(event.getCurrentTicketsInTP() - 1);
                event.setCurrentTicketsInEvent(event.getCurrentTicketsInEvent() + 1);
                eventRepository.save(event);
                eventService.notifyEmitters();

                ticketsPurchased++;
                log.info("Customer purchased 1 ticket. Current pool: {}", event.getCurrentTicketsInTP());

            }

            log.info("Customer purchased {} tickets", ticketsPurchased);
            return "Customer purchased " + ticketsPurchased + " tickets";
        } catch (Exception e) {
            log.error("Unexpected error while purchasing tickets", e);
            return "Unexpected error while purchasing tickets";
        } finally {
            customerLock.unlock();
        }
    }
}