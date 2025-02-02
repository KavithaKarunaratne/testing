package com.cwOOP.ticketingSystem.repository;

import com.cwOOP.ticketingSystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
