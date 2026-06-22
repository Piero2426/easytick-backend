package com.cibertec.notification_service.dto.request;

import com.cibertec.notification_service.model.type.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {

    private Long userId;
    private NotificationType notificationType;
    private String subject;
    private String message;
}
