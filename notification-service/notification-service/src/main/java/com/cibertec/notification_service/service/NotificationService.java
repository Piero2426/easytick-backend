package com.cibertec.notification_service.service;

import com.cibertec.notification_service.dto.request.CreateNotificationRequest;
import com.cibertec.notification_service.dto.response.NotificationListResponse;
import com.cibertec.notification_service.dto.response.NotificationResponse;
import com.cibertec.notification_service.mapper.NotificationMapper;
import com.cibertec.notification_service.model.Notification;
import com.cibertec.notification_service.model.type.NotificationStatus;
import com.cibertec.notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        Notification notification = notificationMapper.toEntity(request);
        notification.setNotificationStatus(NotificationStatus.PENDING); // por defecto pendiente
        notification.setSentAt(LocalDateTime.now()); // o null si se envía después

        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }

    public NotificationResponse getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        return notificationMapper.toResponse(notification);
    }

    public List<NotificationListResponse> getNotificationsByUser(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .map(notificationMapper::toListResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificationResponse updateNotificationStatus(Long id, NotificationStatus status) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setNotificationStatus(status);
        Notification updated = notificationRepository.save(notification);
        return notificationMapper.toResponse(updated);
    }

    public List<NotificationListResponse> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByNotificationStatus(status.name())
                .stream()
                .map(notificationMapper::toListResponse)
                .collect(Collectors.toList());
    }
}
