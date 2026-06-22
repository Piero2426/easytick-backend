package com.cibertec.booking_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.booking_service.model.Booking;
import com.cibertec.booking_service.model.type.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByEventId(Long eventId);
    List<Booking> findByEventIdAndBookingStatus(Long eventId, BookingStatus status);
    long countByEventIdAndBookingStatus(Long eventId, BookingStatus status);

}