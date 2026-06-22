package com.cibertec.auth_service.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
    
    private static final String SECRET_STRING = "mi_clave_super_secreta_de_32_bytes!!";
   private SecretKey key;

   //ojo no cambiar, mantener en .UTF-8 para que el tamaño sea correcto (32 bytes = 256 bits) 
   //y tener coordination con el api-gateway que también usa esta clave para validar los tokens
@PostConstruct
public void init() {
    this.key = Keys.hmacShaKeyFor(
        SECRET_STRING.getBytes(StandardCharsets.UTF_8)
    );
}
    
    // Generar token JWT para un usuario
    public String generateToken(String username) {
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 3600000))
        .signWith(key)
        .compact();
    }
    
    // Obtener los claims (datos) del token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    // Validar token
    public boolean validateToken(String token, String username) {
        try {
            return getClaims(token).getSubject().equals(username);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    // Extraer username del token
    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }
}