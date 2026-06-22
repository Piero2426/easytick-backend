package com.cibertec.payment_service.controller;

import com.cibertec.payment_service.dto.response.PaymentListResponse;
import com.cibertec.payment_service.dto.response.PaymentResponse;
import com.cibertec.payment_service.model.type.PaymentMethod;
import com.cibertec.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/{paymentId}/initiate")
    public ResponseEntity<Map<String, Object>> initiatePayment(
            @PathVariable Long paymentId, 
            @RequestParam PaymentMethod method) {
        return ResponseEntity.ok(paymentService.initiatePaymentFlow(paymentId, method));
    }

    @PostMapping("/capture")
    public ResponseEntity<Map<String, Object>> capturePayment(
            @RequestParam("token") String token,
            @RequestParam("method") PaymentMethod method) {
        
        String cleanToken = token;
        if (token.contains("&")) {
            cleanToken = token.split("&")[0];
        }
        cleanToken = cleanToken.trim();

        return ResponseEntity.ok(paymentService.capturePayment(cleanToken, method));
    }
    @PostMapping("/cancel")
    public ResponseEntity<String> cancelPaymentProcess(@RequestParam("token") String token) {
        String cleanToken = token;
        if (token.contains("&")) {
            cleanToken = token.split("&")[0];
        }
        paymentService.cancelPaymentProcess(cleanToken.trim());
        return ResponseEntity.ok("Pago cancelado y actualizado a FAILED");
    }
    @GetMapping
    public ResponseEntity<List<PaymentListResponse>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<PaymentListResponse>> getPaymentsByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentsByBooking(bookingId));
    }
}