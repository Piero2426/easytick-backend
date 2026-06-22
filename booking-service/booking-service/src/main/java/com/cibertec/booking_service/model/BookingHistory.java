package com.cibertec.booking_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.cibertec.booking_service.model.type.BookingStatus;

@Entity
@Table(name = "booking_history")
@Data
public class BookingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_id")
    private Long bookingId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;


    @Column(name = "changed_at")
    private LocalDateTime changedAt;

    @PrePersist
    public void prePersist() {
        this.changedAt = LocalDateTime.now();
    }

    // getters y setters
}
