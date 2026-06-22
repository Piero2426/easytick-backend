package com.cibertec.auth_service.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cibertec.auth_service.dto.request.LoginDTO;
import com.cibertec.auth_service.dto.request.UserRequestDTO;
import com.cibertec.auth_service.dto.response.AuthResponseDTO;
import com.cibertec.auth_service.dto.response.UserResponseDTO;
import com.cibertec.auth_service.feign.UserResponse;
import com.cibertec.auth_service.mapper.UserMapper;
import com.cibertec.auth_service.model.User;

import com.cibertec.auth_service.repository.UserRepository;
import com.cibertec.auth_service.security.JwtUtil;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserMapper mapper;
    
    
    /**
     * Registrar un nuevo usuario
     */
    public UserResponseDTO registrarUsuario(UserRequestDTO dto) {
        // Validar si el email ya existe
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

         // Validar formato del email
    if (dto.getEmail() == null || !dto.getEmail().contains("@")) {
        throw new IllegalArgumentException("Email inválido: " + dto.getEmail());
    }

        
        // Mapear DTO a entidad
        User usuario = mapper.toEntity(dto);
        
        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        
        // Establecer valores por defecto
        usuario.setEnabled(true);
        usuario.setCreatedAt(LocalDateTime.now());
        
        // Guardar en base de datos
        User usuarioGuardado = repository.save(usuario);
        
        // Enviar mensaje a RabbitMQ (notificación)
        try {
            //authProductor.enviarUser(usuarioGuardado); --por ahora comentado
        } catch (Exception e) {
            System.out.println("Error al enviar mensaje a RabbitMQ: " + e.getMessage());
        }
        
        // Mapear entidad a DTO de respuesta
        return mapper.toResponse(usuarioGuardado);
    }
    
    /**
     * Login de usuario
     */
    public AuthResponseDTO login(LoginDTO dto) {
        // Buscar usuario por email
        User usuario = repository.findByEmail(dto.getEmail())
            .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
        
        // Validar contraseña
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        // Validar que el usuario esté habilitado
        if (!usuario.getEnabled()) {
            throw new RuntimeException("Usuario deshabilitado");
        }
        
        // Generar token JWT
        String token = jwtUtil.generateToken(usuario.getEmail());
        
        // Mapear usuario a DTO de respuesta
        UserResponseDTO userResponse = mapper.toResponse(usuario);
        
        // Retornar token y datos del usuario
        return new AuthResponseDTO(token, userResponse);
    }
    
    /**
     * Obtener usuario por email (para otros servicios)
     */
    public UserResponseDTO obtenerUsuarioPorEmail(String email) {
        User usuario = repository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return mapper.toResponse(usuario);
    }

    public UserResponse obtenerUsuarioPorId(Long id) {

    User user = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getName()
    );
}
}