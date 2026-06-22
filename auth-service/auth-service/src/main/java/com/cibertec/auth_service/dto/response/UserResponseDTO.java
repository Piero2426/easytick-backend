package com.cibertec.auth_service.dto.response;

import java.time.LocalDateTime;

import com.cibertec.auth_service.model.type.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private UserRole roleType;
    private Boolean enabled;
    private LocalDateTime createdAt;
}