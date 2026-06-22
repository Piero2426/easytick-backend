package com.cibertec.booking_service.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.cibertec.booking_service.model.Booking;

@Component
public class BookingProductor {

    private final RabbitTemplate rabbitTemplate;

    public BookingProductor(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarBooking(Booking booking) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfigBooking.EXCHANGE_NAME,
            RabbitMQConfigBooking.ROUTING_KEY,
            booking
        );
    }
}
