package com.cibertec.booking_service.service;

import com.cibertec.booking_service.model.Booking;
import com.cibertec.booking_service.model.type.BookingStatus;
import com.cibertec.booking_service.rabbit.BookingProductor;
import com.cibertec.booking_service.repository.BookingRepository;

import com.cibertec.booking_service.dto.request.CreateBookingRequest;
import com.cibertec.booking_service.dto.request.UpdateBookingStatusRequest;
import com.cibertec.booking_service.dto.response.BookingListResponse;
import com.cibertec.booking_service.dto.response.BookingResponse;
import com.cibertec.booking_service.feign.EventClient;
import com.cibertec.booking_service.feign.EventFeignResponse;
import com.cibertec.booking_service.mapper.BookingMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final EventClient eventClient;
    private final BookingProductor bookingProductor;
    
    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request, Long userId, String token) {
    	EventFeignResponse event;
        try {
             event = eventClient.getEventById(request.getEventId(), token);
        } catch (Exception e) {
            throw new RuntimeException("Error al consultar el Event Service o evento no encontrado: " + e.getMessage());
        }
        if (event.getAvailableSlots() < request.getQuantity()) {
            throw new IllegalStateException("No hay suficientes cupos. Solicitados: " + request.getQuantity() + ", Disponibles: " + event.getAvailableSlots());
        }
        if (!"ACTIVE".equals(event.getEventStatus())) { 
            throw new IllegalStateException("Este evento ha sido desactivado o cancelado y ya no acepta reservas.");
        }

        if (event.getAvailableSlots() < request.getQuantity()) {
            throw new IllegalStateException("No hay suficientes cupos...");
        }
        Booking booking = bookingMapper.toEntity(request);
        booking.setUserId(userId);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTotalPrice(event.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
        booking.setCreatedAt(java.time.LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        try {
            eventClient.reduceSlots(request.getEventId(), request.getQuantity(), token);
        } catch (Exception e) {
            throw new RuntimeException("Error al reservar los cupos en el evento: " + e.getMessage());
        }
        bookingProductor.enviarBooking(savedBooking);
        return bookingMapper.toResponse(savedBooking);
    }

    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        return bookingMapper.toResponse(booking);
    }

    public List<BookingListResponse> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId)
                .stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.CONFIRMED)
                .map(bookingMapper::toListResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public BookingResponse updateBookingStatus(Long bookingId, UpdateBookingStatusRequest request) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        BookingStatus currentStatus = booking.getBookingStatus();
        BookingStatus newStatus = request.getBookingStatus();

        if (currentStatus == BookingStatus.CANCELLED) {
            throw new IllegalStateException("No se puede modificar una reserva cancelada");
        }

        if (currentStatus != BookingStatus.PENDING) {
            throw new IllegalStateException("Solo reservas en estado PENDING pueden cambiar");
        }

        booking.setBookingStatus(newStatus);

        return bookingMapper.toResponse(bookingRepository.save(booking));
    }
    
    @Transactional
    public void procesarBooking(Booking booking) {
        if (booking.getQuantity() == null || booking.getQuantity() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setCreatedAt(java.time.LocalDateTime.now());
        bookingRepository.save(booking);
    }
  
    @Transactional
    public void updateStatusFromPayment(Long bookingId, String paymentStatus) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        if ("COMPLETED".equals(paymentStatus)) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
        } else if ("FAILED".equals(paymentStatus)) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
        }
        
        bookingRepository.save(booking);
    }


}
