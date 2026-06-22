package com.cibertec.payment_service.rabbit;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarConfirmacionPago(Long bookingId, String status) {
        String jsonMessage = String.format("{\"bookingId\": %d, \"status\": \"%s\"}", bookingId, status);
        
        rabbitTemplate.convertAndSend("payment_confirmations_queue", jsonMessage);
        
        System.out.println("Aviso enviado a Booking: Pago " + status + " para reserva " + bookingId);
    }
}