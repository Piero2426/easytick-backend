package com.cibertec.notification_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cibertec.notification_service.dto.request.CreateNotificationRequest;
import com.cibertec.notification_service.dto.response.NotificationListResponse;
import com.cibertec.notification_service.dto.response.NotificationResponse;
import com.cibertec.notification_service.model.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    // Entity → Response
    NotificationResponse toResponse(Notification notification);

    // Entity → List Response
    NotificationListResponse toListResponse(Notification notification);

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notificationStatus", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    Notification toEntity(CreateNotificationRequest request);
}
