package com.almacen.ms_usuarios.controller;

import com.almacen.ms_usuarios.dto.UsuarioRequestDTO;
import com.almacen.ms_usuarios.dto.UsuarioResponseDTO;
import com.almacen.ms_usuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(
    name = "Usuarios",
    description = "Operaciones relacionadas con la gestión de usuarios"
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(
        summary = "Crear usuario",
        description = "Registra un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class),
                examples = @ExampleObject(
                    name = "Usuario Creado",
                    value = """
                    {
                      "id": 1,
                      "nombre": "Carlos Gómez",
                      "email": "carlos@email.com",
                      "rol": "ADMIN"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del usuario a registrar",
                required = true
            )
            @Valid @RequestBody UsuarioRequestDTO request
    ) {

        log.info("Solicitud para crear usuario");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearUsuario(request));
    }

    @GetMapping
    @Operation(
        summary = "Listar usuarios",
        description = "Obtiene todos los usuarios registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {

        log.info("Solicitud para listar usuarios");

        return ResponseEntity.ok(
                usuarioService.listarUsuarios()
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar usuario por ID",
        description = "Obtiene un usuario específico mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        )
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(
            @Parameter(
                description = "ID del usuario",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("Solicitud para obtener usuario con ID: {}", id);

        return ResponseEntity.ok(
                usuarioService.obtenerUsuarioPorId(id)
        );
    }

    @GetMapping("/email/{email}")
    @Operation(
        summary = "Buscar usuario por Email",
        description = "Obtiene un usuario específico mediante su dirección de correo electrónico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        )
    })
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorEmail(
            @Parameter(
                description = "Correo electrónico del usuario",
                required = true,
                example = "usuario@correo.com"
            )
            @PathVariable String email
    ) {

        log.info("Solicitud para obtener usuario con email: {}", email);

        return ResponseEntity.ok(
                usuarioService.obtenerUsuarioPorEmail(email)
        );
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar usuario",
        description = "Actualiza los datos de un usuario existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuario actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @Parameter(
                description = "ID del usuario a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del usuario",
                required = true
            )
            @Valid @RequestBody UsuarioRequestDTO request
    ) {

        log.info("Solicitud para actualizar usuario con ID: {}", id);

        return ResponseEntity.ok(
                usuarioService.actualizarUsuario(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar usuario",
        description = "Elimina un usuario utilizando su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Usuario eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        )
    })
    public ResponseEntity<Void> eliminarUsuario(
            @Parameter(
                description = "ID del usuario a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("Solicitud para eliminar usuario con ID: {}", id);

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}