package com.almacen.ms_auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.almacen.ms_auth.util.JwtUtil;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain

    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authHeader.substring(7);

        if (!jwtUtil.validarToken(token)) {

            filterChain.doFilter(request, response);
            return;
        }

        String email =
                jwtUtil.extraerEmail(token);

        String rol =
                jwtUtil.extraerRol(token);

        log.info("JWT valido para usuario: {}",
                email);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(

                        email,

                        null,

                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + rol
                                )
                        )
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);

        filterChain.doFilter(request, response);
    }
}