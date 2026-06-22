package com.cibertec.payment_service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.cibertec.payment_service.model.type.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentListResponse {

    private Long id;
    private Long bookingId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
}
