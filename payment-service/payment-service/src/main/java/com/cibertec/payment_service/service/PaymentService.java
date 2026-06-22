package com.cibertec.payment_service.service;

import com.cibertec.payment_service.dto.response.PaymentListResponse;
import com.cibertec.payment_service.dto.response.PaymentResponse;
import com.cibertec.payment_service.mapper.PaymentMapper;
import com.cibertec.payment_service.model.Payment;
import com.cibertec.payment_service.model.type.PaymentMethod;
import com.cibertec.payment_service.model.type.PaymentStatus;
import com.cibertec.payment_service.repository.PaymentRepository;
import com.cibertec.payment_service.strategy.PaymentStrategy;
import com.cibertec.payment_service.strategy.PaymentStrategyFactory;
import com.cibertec.payment_service.rabbit.PaymentProducer;
import com.cibertec.payment_service.rabbit.BookingMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyFactory strategyFactory;
    private final PaymentProducer paymentProducer;

    @Transactional
    public void createPendingPaymentFromBooking(BookingMessageDTO bookingMessage) {
        Payment payment = new Payment();
        payment.setBookingId(bookingMessage.getId());
        payment.setAmount(bookingMessage.getTotalPrice());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Transactional
    public Map<String, Object> initiatePaymentFlow(Long paymentId, PaymentMethod method) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentMethod(method);
        
        PaymentStrategy strategy = strategyFactory.getStrategy(method);
        Map<String, Object> gatewayResponse = strategy.initiatePayment(payment);

        payment.setTransactionRef(gatewayResponse.get("id").toString());
        paymentRepository.save(payment);

        return gatewayResponse;
    }

    @Transactional
    public Map<String, Object> capturePayment(String transactionRef, PaymentMethod method) {
        Payment payment = paymentRepository.findByTransactionRef(transactionRef)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));

        PaymentStrategy strategy = strategyFactory.getStrategy(method);
        Map<String, Object> captureResponse = strategy.capturePayment(transactionRef);

        if ("COMPLETED".equals(captureResponse.get("status"))) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);
            paymentProducer.enviarConfirmacionPago(payment.getBookingId(), "COMPLETED");
        }

        return captureResponse;
    }
    @Transactional
    public void cancelPaymentProcess(String transactionRef) {
        Payment payment = paymentRepository.findByTransactionRef(transactionRef)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        
        payment.setPaymentStatus(PaymentStatus.FAILED);
        paymentRepository.save(payment);
        
        paymentProducer.enviarConfirmacionPago(payment.getBookingId(), "FAILED");
    }

    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found con ID: " + id));
        return paymentMapper.toResponse(payment);
    }

    public List<PaymentListResponse> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toListResponse)
                .collect(Collectors.toList());
    }

    public List<PaymentListResponse> getPaymentsByBooking(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .stream()
                .map(paymentMapper::toListResponse)
                .collect(Collectors.toList());
    }
}