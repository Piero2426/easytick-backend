package com.cibertec.payment_service.dto.request;

import com.cibertec.payment_service.model.type.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmPaymentRequest {

    private String transactionRef;
    private PaymentStatus paymentStatus;
}
