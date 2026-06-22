package com.cibertec.payment_service.rabbit;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BookingMessageDTO {
    private Long id; 
    private Long userId;
    private Long eventId;
    private BigDecimal totalPrice;
    private String bookingStatus;
}
