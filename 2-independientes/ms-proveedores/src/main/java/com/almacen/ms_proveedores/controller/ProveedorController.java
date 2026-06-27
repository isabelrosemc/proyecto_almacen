package com.almacen.ms_proveedores.controller;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.almacen.ms_proveedores.dto.ProveedorRequestDTO;
import com.almacen.ms_proveedores.dto.ProveedorResponseDTO;
import com.almacen.ms_proveedores.service.ProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Proveedores",
    description = "Operaciones relacionadas con la gestión de proveedores"
)
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    @Operation(
        summary = "Crear proveedor",
        description = "Registra un nuevo proveedor en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Proveedor creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProveedorResponseDTO.class),
                examples = @ExampleObject(
                    name = "Proveedor Creado",
                    value = """
                    {
                      "id": 1,
                      "nombre": "Proveedor Ejemplo"
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
    public ResponseEntity<ProveedorResponseDTO>
    crearProveedor(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del proveedor a registrar",
                required = true
            )
            @Valid @RequestBody
            ProveedorRequestDTO request
    ) {

        log.info("POST /api/proveedores");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proveedorService.crearProveedor(request));
    }

    @GetMapping
    @Operation(
        summary = "Listar proveedores",
        description = "Obtiene todos los proveedores registrados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<ProveedorResponseDTO>>
    listarProveedores() {

        log.info("GET /api/proveedores");

        return ResponseEntity.ok(
                proveedorService.listarProveedores()
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar proveedor por ID",
        description = "Obtiene un proveedor específico mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Proveedor encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado"
        )
    })
    public ResponseEntity<ProveedorResponseDTO>
    buscarPorId(

            @Parameter(
                description = "ID del proveedor",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("GET /api/proveedores/{}", id);

        return ResponseEntity.ok(
                proveedorService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar proveedor",
        description = "Actualiza los datos de un proveedor existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Proveedor actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProveedorResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<ProveedorResponseDTO>
    actualizarProveedor(

            @Parameter(
                description = "ID del proveedor a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos del proveedor",
                required = true
            )
            @Valid @RequestBody
            ProveedorRequestDTO request
    ) {

        log.info("PUT /api/proveedores/{}", id);

        return ResponseEntity.ok(
                proveedorService.actualizarProveedor(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar proveedor",
        description = "Elimina un proveedor utilizando su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Proveedor eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Proveedor no encontrado"
        )
    })
    public ResponseEntity<Void>
    eliminarProveedor(

            @Parameter(
                description = "ID del proveedor a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("DELETE /api/proveedores/{}", id);

        proveedorService.eliminarProveedor(id);

        return ResponseEntity.noContent().build();
    }
}