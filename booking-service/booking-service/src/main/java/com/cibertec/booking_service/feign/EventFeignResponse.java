package com.cibertec.booking_service.feign;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class EventFeignResponse {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer availableSlots;
    private String eventStatus;
}