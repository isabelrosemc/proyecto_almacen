package com.almacen.ms_compras.controller;

import com.almacen.ms_compras.dto.*;
import com.almacen.ms_compras.service.CompraService;

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
@RequestMapping("/api/compras")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Compras",
    description = "Operaciones relacionadas con el procesamiento de compras"
)
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    @Operation(
        summary = "Crear registro de compra",
        description = "Registra una nueva orden de compra o transacción en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Compra registrada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompraResponseDTO.class),
                examples = @ExampleObject(
                    name = "Compra Creada",
                    value = """
                    {
                      "id": 1,
                      "proveedorId": 3,
                      "total": 1500.50,
                      "fecha": "2026-06-15T13:20:00"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos o inconsistentes"
        )
    })
    public ResponseEntity<CompraResponseDTO> crearCompra(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos requeridos para procesar una compra",
                required = true
            )
            @Valid @RequestBody CompraRequestDTO request
    ) {

        log.info("POST /api/compras");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compraService.crearCompra(request));
    }

    @GetMapping
    @Operation(
        summary = "Listar compras",
        description = "Obtiene todo el historial de compras registradas en el almacén"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de compras obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompraResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<CompraResponseDTO>> listarCompras() {

        log.info("GET /api/compras");

        return ResponseEntity.ok(
                compraService.listarCompras()
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar compra por ID",
        description = "Obtiene el detalle de una compra específica utilizando su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Compra encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CompraResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El ID de la compra no existe en los registros"
        )
    })
    public ResponseEntity<CompraResponseDTO> buscarPorId(
            @Parameter(
                description = "ID único de la compra",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("GET /api/compras/{}", id);

        return ResponseEntity.ok(
                compraService.buscarPorId(id)
        );
    }
}