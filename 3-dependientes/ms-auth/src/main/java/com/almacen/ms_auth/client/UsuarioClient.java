package com.almacen.ms_auth.client;

import com.almacen.ms_auth.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-usuarios", url = "http://localhost:8081")
public interface UsuarioClient {

    @GetMapping("/api/usuarios/correo/{correo}")
    UsuarioDTO obtenerPorCorreo(@PathVariable String correo);
}