package com.almacen.ms_auth.config;

import com.almacen.ms_auth.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                // DESACTIVA CSRF
                .csrf(csrf -> csrf.disable())

                // DESACTIVA LOGIN HTML DE SPRING
                .httpBasic(Customizer.withDefaults())

                // API REST SIN SESION
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // PERMISOS
                .authorizeHttpRequests(auth -> auth

                        // LOGIN PUBLICO
                        .requestMatchers(
                                "/auth/login"
                        ).permitAll()

                        // ADMIN
                        .requestMatchers(
                                "/admin/**"
                        ).hasRole("ADMIN")

                        // OPERADOR
                        .requestMatchers(
                                "/operador/**"
                        ).hasRole("OPERADOR")

                        // TODO LO DEMAS REQUIERE TOKEN
                        .anyRequest()
                        .authenticated()
                )

                // JWT FILTER
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}