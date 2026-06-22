package com.cibertec.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cibertec.auth_service.feign.*;
import com.cibertec.auth_service.dto.request.LoginDTO;
import com.cibertec.auth_service.dto.request.UserRequestDTO;
import com.cibertec.auth_service.dto.response.AuthResponseDTO;
import com.cibertec.auth_service.dto.response.UserResponseDTO;
import com.cibertec.auth_service.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
    
    @Autowired
    private UserService service;
    
    /**
     * Endpoint de registro de usuarios
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrar(@Valid @RequestBody UserRequestDTO dto) {
        try {
            UserResponseDTO usuario = service.registrarUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    /**
     * Endpoint de login
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO dto) {
        try {
            AuthResponseDTO response = service.login(dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
    /**
     * Endpoint para obtener usuario por email (para otros microservicios)
     * GET /api/auth/user/{email}
     * Requiere autenticación (token JWT)
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<?> obtenerUsuarioPorEmail(@PathVariable String email) {
        try {
            UserResponseDTO usuario = service.obtenerUsuarioPorEmail(email);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/user/id/{id}")
public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
    try {
        UserResponse usuario = service.obtenerUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
}