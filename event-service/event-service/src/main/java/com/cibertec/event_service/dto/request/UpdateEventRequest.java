package com.cibertec.event_service.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventRequest {

    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String latitud;
    private String longitud;
    private String location;
    private BigDecimal price;
    private Integer capacity;
    private Long categoryId;
}
