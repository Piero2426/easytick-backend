package com.cibertec.event_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cibertec.event_service.model.type.EventStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventListResponse {

    private Long id;
    private String title;
    private LocalDateTime eventDate;
    private String latitud;
    private String longitud;
    private String location;
    private BigDecimal price;
    private Integer availableSlots;
    private EventStatus eventStatus;
    private String imageUrl;
    private String category;
}
