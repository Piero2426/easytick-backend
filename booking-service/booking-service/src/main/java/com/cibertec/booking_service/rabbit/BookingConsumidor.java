package com.cibertec.booking_service.rabbit;

import org.springframework.stereotype.Component;

import com.cibertec.booking_service.model.Booking;
import com.cibertec.booking_service.service.BookingService;


@Component
public class BookingConsumidor {

    private final BookingService bookingService;

    public BookingConsumidor(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void recibirBooking(Booking booking) {
        bookingService.procesarBooking(booking);
    }
    
}
