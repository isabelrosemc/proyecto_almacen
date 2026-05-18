package com.almacen.ms_auth.service.impl;

import com.almacen.ms_auth.client.UsuarioClient;
import com.almacen.ms_auth.dto.*;
import com.almacen.ms_auth.exception.UnauthorizedException;
import com.almacen.ms_auth.service.AuthService;
import com.almacen.ms_auth.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UsuarioClient usuarioClient;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(
            LoginRequestDTO request
    ) {

        log.info("Intentando login para email: {}",
                request.getEmail());

        UsuarioResponseDTO usuario;

        try {

            usuario = usuarioClient.obtenerUsuarioPorEmail(
                    request.getEmail()
            );

        } catch (Exception ex) {

            log.error("Usuario no encontrado");

            throw new UnauthorizedException(
                    "Credenciales invalidas"
            );
        }

        log.info("PASSWORD REQUEST: {}",
        request.getPassword());

        log.info("PASSWORD BD: {}",
                usuario.getPassword());

        if (!passwordEncoder.matches(

        request.getPassword(),

        usuario.getPassword()

        )) {

        log.error("Password incorrecta");

        throw new UnauthorizedException(
                "Credenciales invalidas"
        );
        }

        if (!usuario.getEstado()) {

            log.error("Usuario inactivo");

            throw new UnauthorizedException(
                    "Usuario inactivo"
            );
        }

        String token = jwtUtil.generarToken(
                usuario.getEmail(),
                usuario.getRol()
        );

        log.info("JWT generado correctamente");

        return LoginResponseDTO.builder()
                .token(token)
                .tipo("Bearer")
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .build();
    }
}