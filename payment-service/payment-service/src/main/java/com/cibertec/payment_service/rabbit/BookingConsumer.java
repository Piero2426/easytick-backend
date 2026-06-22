package com.cibertec.payment_service.rabbit;

import com.cibertec.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = RabbitMQConfigPayment.BOOKING_QUEUE)
    public void receiveBooking(BookingMessageDTO booking) {
        paymentService.createPendingPaymentFromBooking(booking);
    }
}