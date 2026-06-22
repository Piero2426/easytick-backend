package com.cibertec.event_service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.cibertec.event_service.dto.request.CreateEventRequest;
import com.cibertec.event_service.dto.request.UpdateEventRequest;
import com.cibertec.event_service.dto.response.EventListResponse;
import com.cibertec.event_service.dto.response.EventResponse;
import com.cibertec.event_service.model.Event;

@Mapper(componentModel = "spring")
public interface EventMapper {

	@Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "category")
    EventResponse toResponse(Event event);

    @Mapping(source = "category.name", target = "category")
    EventListResponse toListResponse(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizerId", ignore = true)
    @Mapping(target = "availableSlots", ignore = true)
    @Mapping(target = "eventStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "category", ignore = true) 
    Event toEntity(CreateEventRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizerId", ignore = true)
    @Mapping(target = "availableSlots", ignore = true)
    @Mapping(target = "eventStatus", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "category", ignore = true) 
    void updateEntity(UpdateEventRequest request, @MappingTarget Event event);
}