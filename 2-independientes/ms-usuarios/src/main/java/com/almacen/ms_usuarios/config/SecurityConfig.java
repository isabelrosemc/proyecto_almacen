package com.almacen.ms_usuarios.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth
                        // 1. Rutas públicas de tu API de usuarios
                        .requestMatchers(
                                "/api/usuarios/**"
                        ).permitAll()

                        // 2. Rutas públicas de Swagger y OpenAPI para que no den Error 403
                        .requestMatchers(
                                "/doc/swagger-ui.html",
                                "/doc/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // 3. Cualquier otra ruta requerirá estar autenticado
                        .anyRequest()
                        .authenticated()
                );

        return http.build();
    }
}