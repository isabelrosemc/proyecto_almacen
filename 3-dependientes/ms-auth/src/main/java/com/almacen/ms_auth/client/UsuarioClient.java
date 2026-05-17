package com.almacen.ms_auth.client;

import com.almacen.ms_auth.dto.UsuarioResponseDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-usuarios")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/email/{email}")
    UsuarioResponseDTO obtenerUsuarioPorEmail(
            @PathVariable String email
    );
}