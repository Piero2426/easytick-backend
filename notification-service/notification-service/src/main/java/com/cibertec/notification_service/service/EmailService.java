package com.cibertec.notification_service.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cibertec.notification_service.rabbit.EventMessageDTO;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // Nuevo método para enviar correo HTML elegante
    public void sendEventEmail(String to, EventMessageDTO event) {
        try {
            if (to == null || !to.contains("@")) {
                throw new IllegalArgumentException("Email inválido: " + to);
            }

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("🎉 ¡Evento creado: " + event.getTitle() + "!");

            // HTML elegante
            String html = "<div style='max-width:600px; margin:auto; border:1px solid #ccc; padding:20px; "
                    + "font-family:Arial, sans-serif; text-align:center; background-color:#f9f9f9;'>"
                    + "<h2 style='color:#333;'>¡Tu evento fue creado con éxito!</h2>"
                    + "<p><strong>" + event.getTitle() + "</strong></p>"
                    + (event.getImageUrl() != null ? "<img src='" + event.getImageUrl() + "' "
                    + "style='max-width:100%; height:auto; border-radius:10px; margin:10px 0;'/>" : "")
                    + "<p style='text-align:left;'><strong>Descripción:</strong> " + event.getDescription() + "</p>"
                    + "<p style='text-align:left;'><strong>Ubicación:</strong> " + event.getLocation() + "</p>"
                    + "<p style='text-align:left;'><strong>Fecha:</strong> " + event.getEventDate() + "</p>"
                    + "<p style='color:#888; font-size:12px; margin-top:20px;'>Gracias por usar nuestra plataforma.</p>"
                    + "</div>";

            helper.setText(html, true);

            mailSender.send(message);
            System.out.println("✅ Correo HTML enviado correctamente a " + to);

        } catch (Exception e) {
            System.out.println("❌ Error enviando correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}