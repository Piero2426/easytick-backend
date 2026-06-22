package com.cibertec.notification_service.dto.response;

import java.time.LocalDateTime;

import com.cibertec.notification_service.model.Notification;
import com.cibertec.notification_service.model.type.NotificationStatus;
import com.cibertec.notification_service.model.type.NotificationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationListResponse {

    private Long id;
    private NotificationType notificationType;
    private String subject;
    private NotificationStatus status;
    private LocalDateTime sentAt;
}
