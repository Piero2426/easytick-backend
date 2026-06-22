package com.cibertec.event_service.controller;

import com.cibertec.event_service.dto.request.CreateEventRequest;
import com.cibertec.event_service.dto.request.UpdateEventRequest;
import com.cibertec.event_service.dto.response.EventListResponse;
import com.cibertec.event_service.dto.response.EventResponse;
import com.cibertec.event_service.model.EventCategory;
import com.cibertec.event_service.model.type.EventStatus;
import com.cibertec.event_service.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

	private final EventService eventService;
	private final ObjectMapper objectMapper;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<EventResponse> createEvent(@RequestPart("event") String eventJson,
			@RequestPart(value = "image", required = false) MultipartFile image,
			@RequestHeader("Authorization") String token) throws Exception {

		CreateEventRequest request = objectMapper.readValue(eventJson, CreateEventRequest.class);

		Long organizerId = request.getOrganizerId();
		System.out.println("TOKEN QUE LLEGA DEL FRONT: [" + token + "]");
		return ResponseEntity.ok(eventService.createEvent(request, image, organizerId, token));
	}

	@GetMapping("/categories")
	public ResponseEntity<List<EventCategory>> getAllCategories() {
		return ResponseEntity.ok(eventService.getAllCategories());
	}

	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<EventListResponse>> getEventsByCategory(@PathVariable Long categoryId) {
		return ResponseEntity.ok(eventService.getEventsByCategory(categoryId));
	}

	@GetMapping("/active")
	public ResponseEntity<List<EventListResponse>> getActiveEvents() {
		return ResponseEntity.ok(eventService.getActiveEvents());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EventResponse> getEvent(@PathVariable Long id) {
		return ResponseEntity.ok(eventService.getEventById(id));
	}

	@GetMapping
	public ResponseEntity<List<EventListResponse>> getAllEvents() {
		return ResponseEntity.ok(eventService.getAllEvents());
	}

	@PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id, 
            @RequestPart("event") String eventJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        
        // Convertimos el JSON String al DTO
        UpdateEventRequest request = objectMapper.readValue(eventJson, UpdateEventRequest.class);
        
        // Pasamos la imagen al servicio
        return ResponseEntity.ok(eventService.updateEvent(id, request, image));
    }
	@GetMapping("/organizer/{organizerId}")
	public ResponseEntity<List<EventListResponse>> getEventsByOrganizer(@PathVariable Long organizerId) {
		return ResponseEntity.ok(eventService.getEventsByOrganizer(organizerId));
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<EventResponse> changeEventStatus(@PathVariable Long id, @RequestParam String status) {
		EventStatus newStatus = EventStatus.valueOf(status.toUpperCase());
		return ResponseEntity.ok(eventService.changeEventStatus(id, newStatus));
	}

	@PostMapping("/{id}/reduce-slots")
	public ResponseEntity<String> reduceSlots(@PathVariable Long id, @RequestParam Integer quantity) {

		eventService.reduceAvailableSlots(id, quantity);
		return ResponseEntity.ok("Cupos restados exitosamente");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long id) {
		eventService.deleteEvent(id);
		return ResponseEntity.ok("Evento eliminado correctamente");
	}

}
