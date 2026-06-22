package com.cibertec.booking_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cibertec.booking_service.dto.request.CreateBookingRequest;
import com.cibertec.booking_service.dto.response.BookingListResponse;
import com.cibertec.booking_service.dto.response.BookingResponse;
import com.cibertec.booking_service.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    // Entity → Response
    BookingResponse toResponse(Booking booking);

    // Entity → List Response
    BookingListResponse toListResponse(Booking booking);

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookingStatus", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    Booking toEntity(CreateBookingRequest request);
}

