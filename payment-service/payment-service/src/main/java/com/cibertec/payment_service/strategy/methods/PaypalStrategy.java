package com.cibertec.payment_service.strategy.methods;
import com.cibertec.payment_service.model.Payment;
import com.cibertec.payment_service.model.type.PaymentMethod;
import com.cibertec.payment_service.service.PaypalService;
import com.cibertec.payment_service.strategy.PaymentStrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaypalStrategy implements PaymentStrategy {

    private final PaypalService paypalService;

    @Override
    public PaymentMethod getPaymentMethod() {
        return PaymentMethod.PAYPAL;
    }

    @Override
    public Map<String, Object> initiatePayment(Payment payment) {
        Map<String, Object> response = paypalService.createOrder(payment.getAmount().doubleValue());
        return response; 
    }

    @Override
    public Map<String, Object> capturePayment(String transactionToken) {
 
        return paypalService.captureOrder(transactionToken);
    }
}
