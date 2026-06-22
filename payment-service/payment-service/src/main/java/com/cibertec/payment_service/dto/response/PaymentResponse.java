package com.cibertec.payment_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cibertec.payment_service.model.type.PaymentMethod;
import com.cibertec.payment_service.model.type.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private Long bookingId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionRef;
    private LocalDateTime createdAt;
}
