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

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @Valid @RequestBody UsuarioRequestDTO request
    ) {

        log.info("Solicitud para crear usuario");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(request));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

        log.info("Solicitud para listar usuarios");

        return ResponseEntity.ok(
                usuarioService.listarUsuarios()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(
            @PathVariable Long id
    ) {

        log.info("Solicitud para obtener usuario con ID: {}", id);

        return ResponseEntity.ok(
                usuarioService.obtenerUsuarioPorId(id)
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorEmail(
            @PathVariable String email
    ) {

        log.info("Solicitud para obtener usuario con email: {}", email);

        return ResponseEntity.ok(
                usuarioService.obtenerUsuarioPorEmail(email)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Long id,

            @Valid
            @RequestBody UsuarioRequestDTO request
    ) {

        log.info("Solicitud para actualizar usuario con ID: {}", id);

        return ResponseEntity.ok(
                usuarioService.actualizarUsuario(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(
            @PathVariable Long id
    ) {

        log.info("Solicitud para eliminar usuario con ID: {}", id);

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}