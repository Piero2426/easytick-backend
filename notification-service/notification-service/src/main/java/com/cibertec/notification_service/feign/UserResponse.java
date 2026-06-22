package com.cibertec.notification_service.feign;


import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}