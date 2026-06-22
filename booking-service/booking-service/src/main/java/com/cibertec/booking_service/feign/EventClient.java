package com.cibertec.booking_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "event-service", path = "/api/events")
public interface EventClient {

    @GetMapping("/{id}")
    EventFeignResponse getEventById(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String token
    );
    @PostMapping("/{id}/reduce-slots")
    void reduceSlots(
            @PathVariable("id") Long id,
            @RequestParam("quantity") Integer quantity,
            @RequestHeader("Authorization") String token
    );
}