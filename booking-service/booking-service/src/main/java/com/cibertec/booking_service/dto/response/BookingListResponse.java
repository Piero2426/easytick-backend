package com.cibertec.booking_service.dto.response;

import java.math.BigDecimal;

import com.cibertec.booking_service.model.type.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingListResponse {

    private Long id;
    private Long eventId;
    private Integer quantity;
    private BookingStatus bookingStatus;
    private BigDecimal totalPrice;
}

