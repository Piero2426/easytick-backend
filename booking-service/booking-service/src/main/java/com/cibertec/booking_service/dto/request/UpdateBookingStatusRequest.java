package com.cibertec.booking_service.dto.request;

import com.cibertec.booking_service.model.type.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingStatusRequest {

    private BookingStatus bookingStatus;
}
