package com.cibertec.notification_service.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notification_queue")
public class NotificationQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_type")
    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private Boolean processed;

    // getters y setters
}

