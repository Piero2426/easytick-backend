package com.cibertec.event_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.event_service.model.Event;
import com.cibertec.event_service.model.type.EventStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	List<Event> findByEventStatusOrderByIdDesc(EventStatus eventStatus);
    List<Event> findByCategoryId(Long categoryId);

    List<Event> findByEventDateAfter(LocalDateTime date);
    
    List<Event> findByOrganizerId(Long organizerId);
}
