package com.cibertec.payment_service.dto.request;

import com.cibertec.payment_service.model.Payment;
import com.cibertec.payment_service.model.type.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    private Long bookingId;
    private PaymentMethod paymentMethod;
}
