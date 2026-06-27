package com.almacen.ms_ventas.controller;

import com.almacen.ms_ventas.dto.*;
import com.almacen.ms_ventas.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Ventas",
    description = "Operaciones relacionadas con la gestión y consulta de ventas"
)
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    @Operation(
        summary = "Registrar una venta",
        description = "Procesa y registra una nueva venta junto con sus detalles asociados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Venta registrada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VentaResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de la venta inválidos"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al registrar la venta"
        )
    })
    public ResponseEntity<VentaResponseDTO> crearVenta(
            @Valid @RequestBody VentaRequestDTO request
    ) {

        log.info("POST /api/ventas");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ventaService.crearVenta(request)
                );
    }

    @GetMapping
    @Operation(
        summary = "Listar ventas",
        description = "Obtiene el listado completo de ventas registradas en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de ventas obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VentaResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al obtener las ventas"
        )
    })
    public ResponseEntity<List<VentaResponseDTO>> listarVentas() {

        log.info("GET /api/ventas");

        return ResponseEntity.ok(
                ventaService.listarVentas()
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar venta por ID",
        description = "Obtiene la información de una venta específica a partir de su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Venta encontrada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VentaResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró la venta solicitada"
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al consultar la venta"
        )
    })
    public ResponseEntity<VentaResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {

        log.info("GET /api/ventas/{}", id);

        return ResponseEntity.ok(
                ventaService.buscarPorId(id)
        );
    }
}