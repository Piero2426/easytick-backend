package com.cibertec.event_service.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;



@Component
public class EventProductor {

    private final RabbitTemplate rabbitTemplate;

    public EventProductor(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEvento(EventMessageDTO message) {

            System.out.println("📤 Enviando evento a Rabbit: " + message);

        rabbitTemplate.convertAndSend(
            RabbitMQConfigEvent.EXCHANGE_NAME,
            RabbitMQConfigEvent.ROUTING_KEY,
            message
        );
    }
}
