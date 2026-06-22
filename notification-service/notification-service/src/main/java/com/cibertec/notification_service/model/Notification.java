package com.cibertec.notification_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.cibertec.notification_service.model.type.NotificationStatus;
import com.cibertec.notification_service.model.type.NotificationType;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private NotificationType notificationType;

    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    // getters y setters
}
