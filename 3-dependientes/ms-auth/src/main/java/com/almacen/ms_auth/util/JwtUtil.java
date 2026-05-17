package com.almacen.ms_auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key key;

    @PostConstruct
    public void init() {

        this.key = Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    public String generarToken(
            String email,
            String rol
    ) {

        log.info("Generando JWT para usuario: {}", email);

        return Jwts.builder()

                .setSubject(email)

                .claim("rol", rol)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration
                        )
                )

                .signWith(key, SignatureAlgorithm.HS256)

                .compact();
    }

    public String extraerEmail(
            String token
    ) {

        return obtenerClaims(token)
                .getSubject();
    }

    public String extraerRol(
            String token
    ) {

        return obtenerClaims(token)
                .get("rol", String.class);
    }

    public boolean validarToken(
            String token
    ) {

        try {

            obtenerClaims(token);

            return true;

        } catch (JwtException ex) {

            log.error("JWT invalido: {}", ex.getMessage());

            return false;
        }
    }

    private Claims obtenerClaims(
            String token
    ) {

        return Jwts.parserBuilder()

                .setSigningKey(key)

                .build()

                .parseClaimsJws(token)

                .getBody();
    }
}