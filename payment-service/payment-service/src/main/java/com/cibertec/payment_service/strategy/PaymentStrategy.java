package com.cibertec.payment_service.strategy;

import java.util.Map;

import com.cibertec.payment_service.model.Payment;
import com.cibertec.payment_service.model.type.PaymentMethod;

public interface PaymentStrategy {
	PaymentMethod getPaymentMethod();
   
    Map<String, Object> initiatePayment(Payment payment);
    
    Map<String, Object> capturePayment(String transactionToken);
}
