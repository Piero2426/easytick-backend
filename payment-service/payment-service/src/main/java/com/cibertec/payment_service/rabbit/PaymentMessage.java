package com.cibertec.payment_service.rabbit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessage {
    private Long bookingId;
    private String status;
}