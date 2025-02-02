package com.cwOOP.ticketingSystem.service;

import com.cwOOP.ticketingSystem.dto.EventDTO;

import com.cwOOP.ticketingSystem.entity.Event;
import com.cwOOP.ticketingSystem.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    public EventService(EventRepository eventRepository){
        this.eventRepository=eventRepository;
    }
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public String createEvent(EventDTO eventDTO) {
        if (eventDTO.getTotalTickets() < 0 ||
                eventDTO.getTicketReleaseRate() < 0 ||
                eventDTO.getCustomerRetrievalRate() < 0 ||
                eventDTO.getMaxTicketCapacity() < 0) {
            log.error("Negative values are not allowed for event creation.");
            return "Negative values are not allowed for event fields.";

        }
        if(eventDTO.getTotalTickets()<eventDTO.getMaxTicketCapacity()){
            return "Total tickets cannot be less than max ticket capacity.";
        }

        Event event = new Event();
        event.setTotalTickets(eventDTO.getTotalTickets());
        event.setTicketReleaseRate(eventDTO.getTicketReleaseRate());
        event.setCustomerRetrievalRate(eventDTO.getCustomerRetrievalRate());
        event.setMaxTicketCapacity(eventDTO.getMaxTicketCapacity());
        eventRepository.save(event);
        log.info("A new event created.");
        return "Event saved.";
    }

    public SseEmitter addEmitter() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // Set a large timeout
        emitters.add(emitter);

        // Remove emitter on completion or timeout
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        return emitter;
    }
    public void notifyEmitters() {
        Event event = eventRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(event.getCurrentTicketsInTP());
            } catch (IOException e) {
                emitters.remove(emitter); // Remove broken connections
            }
        }
    }



}
