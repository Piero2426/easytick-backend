package com.cibertec.notification_service.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.cibertec.notification_service.feign.AuthClient;
import com.cibertec.notification_service.feign.UserResponse;
import com.cibertec.notification_service.model.type.NotificationStatus;
import com.cibertec.notification_service.model.type.NotificationType;
import com.cibertec.notification_service.service.EmailService;
import com.cibertec.notification_service.service.NotificationService;
import com.cibertec.notification_service.dto.request.CreateNotificationRequest;
import com.cibertec.notification_service.dto.response.NotificationResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventConsumer {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AuthClient authClient;

    @RabbitListener(queues = RabbitMQConfigNotificacion.QUEUE_NAME)
    public void handleEvent(EventMessageDTO message) {
        try {
            // 1️⃣ Obtener datos del usuario desde AuthClient
            UserResponse user = authClient.getUserById(message.getOrganizerId(), message.getToken());

            // 2️⃣ Guardar notificación en la DB
            CreateNotificationRequest request = new CreateNotificationRequest(
                    user.getId(),
                    NotificationType.EVENT_CREATED,
                    "Evento creado",
                    "Tu evento '" + message.getTitle() + "' fue creado correctamente."
            );

            NotificationResponse savedNotification = notificationService.createNotification(request);

            // 3️⃣ Enviar correo elegante con HTML
            String userEmail = user.getEmail();
            System.out.println("EMAIL DEL USUARIO: [" + userEmail + "]");

            if (userEmail != null && userEmail.contains("@")) {
                emailService.sendEventEmail(userEmail, message);  // <-- Aquí usamos el método HTML
            } else {
                System.out.println("❌ Email inválido, no se enviará correo: " + userEmail);
            }

            // 4️⃣ Actualizar estado de la notificación
            notificationService.updateNotificationStatus(savedNotification.getId(), NotificationStatus.SENT);

        } catch (Exception e) {
            System.out.println("Error procesando mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}