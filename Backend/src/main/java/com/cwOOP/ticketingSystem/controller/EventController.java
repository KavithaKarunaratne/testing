package com.cwOOP.ticketingSystem.controller;

import com.cwOOP.ticketingSystem.dto.EventDTO;
import com.cwOOP.ticketingSystem.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/event")
@CrossOrigin(origins = "http://localhost:5173")
public class EventController {
    @Autowired
    private EventService eventService;
    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @PostMapping("/create")
    public String createEvent(@RequestBody EventDTO eventDTO){
        return eventService.createEvent(eventDTO);
    }

    @GetMapping("/stream")
    public SseEmitter streamTickets(){
        return eventService.addEmitter();
    }


}
