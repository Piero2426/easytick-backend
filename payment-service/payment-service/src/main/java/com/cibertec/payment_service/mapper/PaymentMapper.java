package com.cibertec.payment_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.cibertec.payment_service.dto.request.ConfirmPaymentRequest;
import com.cibertec.payment_service.dto.request.CreatePaymentRequest;
import com.cibertec.payment_service.dto.response.PaymentListResponse;
import com.cibertec.payment_service.dto.response.PaymentResponse;
import com.cibertec.payment_service.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    // Entity → Response
    PaymentResponse toResponse(Payment payment);

    // Entity → List Response
    PaymentListResponse toListResponse(Payment payment);

    // Request → Entity (CREATE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "transactionRef", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Payment toEntity(CreatePaymentRequest request);

    // Request → Entity (CONFIRM PAYMENT)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookingId", ignore = true)
    @Mapping(target = "amount", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void confirmPayment(ConfirmPaymentRequest request, @MappingTarget Payment payment);
}
