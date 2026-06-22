package com.cibertec.event_service.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventMessageDTO {

    private Long eventId;
    private String title;
    private String description;
    private String imageUrl;
    private String location;
    private String eventDate; // lo podemos mandar como String para el correo
    private Long organizerId;
    private String token;
}