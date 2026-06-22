package com.cibertec.auth_service.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.cibertec.auth_service.model.User;


@Component
public class AuthProductor {

    private final RabbitTemplate rabbitTemplate;

    public AuthProductor(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarUser(User user) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfigAuth.EXCHANGE_NAME,
            RabbitMQConfigAuth.ROUTING_KEY,
            user
        );
    }
}
