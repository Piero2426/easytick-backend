package com.cibertec.booking_service.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessage implements Serializable {

    private Long bookingId;
    private String status; 

}