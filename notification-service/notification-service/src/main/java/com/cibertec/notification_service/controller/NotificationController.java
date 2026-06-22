package com.cibertec.notification_service.controller;

import com.cibertec.notification_service.dto.request.CreateNotificationRequest;
import com.cibertec.notification_service.dto.response.NotificationListResponse;
import com.cibertec.notification_service.dto.response.NotificationResponse;
import com.cibertec.notification_service.model.type.NotificationStatus;
import com.cibertec.notification_service.service.NotificationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody CreateNotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getNotification(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationListResponse>> getNotificationsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUser(userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<NotificationResponse> updateStatus(@PathVariable Long id,
                                                             @RequestParam NotificationStatus status) {
        return ResponseEntity.ok(notificationService.updateNotificationStatus(id, status));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<NotificationListResponse>> getNotificationsByStatus(@PathVariable NotificationStatus status) {
        return ResponseEntity.ok(notificationService.getNotificationsByStatus(status));
    }
}
