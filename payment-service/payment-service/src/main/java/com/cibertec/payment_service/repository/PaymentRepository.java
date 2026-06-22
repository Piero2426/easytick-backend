package com.cibertec.payment_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.payment_service.model.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingId(Long bookingId);
    List<Payment> findByPaymentStatus(String status);
	Optional<Payment> findByTransactionRef(String transactionRef);
}
