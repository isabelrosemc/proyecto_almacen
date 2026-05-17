package com.almacen.ms_auth.service.impl;

import com.almacen.ms_auth.client.UsuarioClient;
import com.almacen.ms_auth.dto.*;
import com.almacen.ms_auth.exception.UnauthorizedException;
import com.almacen.ms_auth.security.JwtUtil;
import com.almacen.ms_auth.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UsuarioClient usuarioClient;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO login(LoginRequestDTO dto) {

        log.info("Intentando login usuario: {}", dto.getCorreo());

        UsuarioDTO usuario =
                usuarioClient.obtenerPorCorreo(dto.getCorreo());

        if (!usuario.getPassword().equals(dto.getPassword())) {

            log.error("Contraseña incorrecta");

            throw new UnauthorizedException(
                    "Credenciales inválidas");
        }

        if (!usuario.getActivo()) {

            log.error("Usuario inactivo");

            throw new UnauthorizedException(
                    "Usuario inactivo");
        }

        String token = jwtUtil.generarToken(
                usuario.getCorreo(),
                usuario.getRol());

        log.info("Login exitoso usuario: {}", usuario.getCorreo());

        return AuthResponseDTO.builder()
                .token(token)
                .tipo("Bearer")
                .rol(usuario.getRol())
                .build();
    }
}