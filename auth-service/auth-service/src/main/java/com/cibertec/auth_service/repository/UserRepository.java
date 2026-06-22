package com.cibertec.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.auth_service.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Método para buscar usuario por email (necesario para login)
    Optional<User> findByEmail(String email);
    
    // Método para verificar si existe un usuario con ese email (para evitar duplicados)
    boolean existsByEmail(String email);
}