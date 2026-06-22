package com.cibertec.booking_service.rabbit;

import com.cibertec.booking_service.service.BookingService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component

public class PaymentConsumer {

    private final BookingService bookingService;
    private final ObjectMapper objectMapper;
    //para websocket
    private final SimpMessagingTemplate messagingTemplate;
    
    public PaymentConsumer(BookingService bookingService, SimpMessagingTemplate messagingTemplate) {
        this.bookingService = bookingService;
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = new ObjectMapper();
    }

   @RabbitListener(queues = "payment_confirmations_queue") 
    public void receivePaymentConfirmation(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long bookingId = jsonNode.get("bookingId").asLong();
            String status = jsonNode.get("status").asText();
            
            System.out.println("✅ BOOKING RECIBIÓ EL MENSAJE: " + status + " para reserva " + bookingId);
            
            bookingService.updateStatusFromPayment(bookingId, status);
            
            var reserva = bookingService.getBookingById(bookingId);
            Long userId = reserva.getUserId();
            
            String destinoWebSocket = "/topic/user/" + userId + "/tickets";
            messagingTemplate.convertAndSend(destinoWebSocket, message);
            
            System.out.println("📡 ALERTA WEBSOCKET ENVIADA AL CANAL DEL USUARIO: " + destinoWebSocket);
            
        } catch (Exception e) {
            System.err.println("❌ Error al procesar confirmación: " + e.getMessage());
        }
    }
}