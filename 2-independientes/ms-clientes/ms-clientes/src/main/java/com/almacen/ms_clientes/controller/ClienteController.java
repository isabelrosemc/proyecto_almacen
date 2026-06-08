package com.almacen.ms_clientes.controller;

import com.almacen.ms_clientes.dto.ClienteRequestDTO;
import com.almacen.ms_clientes.dto.ClienteResponseDTO;
import com.almacen.ms_clientes.service.ClienteService;

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

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Clientes",
    description = "Operaciones relacionadas con la gestión de clientes"
)
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    @Operation(
        summary = "Crear cliente",
        description = "Registra un nuevo cliente en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Cliente creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class),
                examples = @ExampleObject(
                    name = "Cliente Creado",
                    value = """
                    {
                      "id": 1,
                      "nombre": "Juan Pérez",
                      "correo": "juan@email.com"
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
    public ResponseEntity<ClienteResponseDTO> crear(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del cliente a registrar",
                required = true
            )
            @Valid @RequestBody ClienteRequestDTO dto) {

        log.info("POST /api/clientes");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    @GetMapping
    @Operation(
        summary = "Listar clientes",
        description = "Obtiene todos los clientes registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<ClienteResponseDTO>> listar() {

        log.info("GET /api/clientes");

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar cliente por ID",
        description = "Obtiene un cliente específico mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
        )
    })
    public ResponseEntity<ClienteResponseDTO> buscarPorId(

            @Parameter(
                description = "ID del cliente",
                required = true,
                example = "1"
            )
            @PathVariable Long id) {

        log.info("GET /api/clientes/{}", id);

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar cliente",
        description = "Actualiza los datos de un cliente existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Cliente actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<ClienteResponseDTO> actualizar(

            @Parameter(
                description = "ID del cliente a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del cliente",
                required = true
            )
            @Valid @RequestBody ClienteRequestDTO dto) {

        log.info("PUT /api/clientes/{}", id);

        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar cliente",
        description = "Elimina un cliente utilizando su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Cliente eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado"
        )
    })
    public ResponseEntity<Void> eliminar(

            @Parameter(
                description = "ID del cliente a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id) {

        log.info("DELETE /api/clientes/{}", id);

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}