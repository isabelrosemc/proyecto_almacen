package com.almacen.ms_usuarios.controller;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(
            @Valid @RequestBody UsuarioRequestDTO dto) {

        log.info("POST /api/usuarios");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crearUsuario(dto));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listar() {

        log.info("GET /api/usuarios");

        return ResponseEntity.ok(service.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtener(
            @PathVariable Long id) {

        log.info("GET /api/usuarios/{}", id);

        return ResponseEntity.ok(service.obtenerUsuario(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {

        log.info("PUT /api/usuarios/{}", id);

        return ResponseEntity.ok(service.actualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.info("DELETE /api/usuarios/{}", id);

        service.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorCorreo(
            @PathVariable String correo) {

        log.info("GET /api/usuarios/correo/{}", correo);

        return ResponseEntity.ok(service.obtenerPorCorreo(correo));
}
}