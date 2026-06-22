package com.cibertec.auth_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.cibertec.auth_service.model.type.UserRole;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)   // Guarda "CUSTOMER", "ADMIN", etc
    private UserRole roleType;

    private Boolean enabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    
}

