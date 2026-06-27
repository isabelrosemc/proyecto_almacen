package com.almacen.ms_pagos.controller;

import com.almacen.ms_pagos.dto.PagoDTO;
import com.almacen.ms_pagos.service.PagoService;

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
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Pagos",
    description = "Operaciones relacionadas con el procesamiento y control de pagos"
)
public class PagoController {

    private final PagoService service;

    @GetMapping
    @Operation(
        summary = "Listar pagos",
        description = "Obtiene un historial de todos los pagos registrados en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de pagos obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PagoDTO.class)
            )
        )
    })
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        log.info("GET /api/pagos");
        return ResponseEntity.ok(service.listarPagos());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar pago por ID",
        description = "Obtiene los detalles de un pago específico utilizando su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pago encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PagoDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El ID de pago no existe en los registros"
        )
    })
    public ResponseEntity<PagoDTO> buscarPago(
            @Parameter(
                description = "ID único del pago",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {
        log.info("GET /api/pagos/{}", id);
        return ResponseEntity.ok(service.buscarPago(id));
    }

    @PostMapping
    @Operation(
        summary = "Registrar un pago",
        description = "Procesa y almacena una nueva transacción de pago"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Pago registrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PagoDTO.class),
                examples = @ExampleObject(
                    name = "Pago Registrado",
                    value = """
                    {
                      "id": 1,
                      "compraId": 12,
                      "monto": 450.75,
                      "metodoPago": "TARJETA",
                      "estado": "APROBADO"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Estructura de datos inválida o inconsistente"
        )
    })
    public ResponseEntity<PagoDTO> guardarPago(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos requeridos para emitir un nuevo pago",
                required = true
            )
            @Valid @RequestBody PagoDTO dto
    ) {
        log.info("POST /api/pagos");
        // Nota: Se cambió a HttpStatus.CREATED (201) de manera opcional para alinearse al patrón RESTful de tus otros microservicios
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardarPago(dto));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar pago",
        description = "Modifica los datos o el estado de un registro de pago existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pago actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = PagoDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El pago especificado no fue encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Parámetros de actualización inválidos"
        )
    })
    public ResponseEntity<PagoDTO> actualizarPago(
            @Parameter(
                description = "ID del pago a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos correspondientes al pago",
                required = true
            )
            @Valid @RequestBody PagoDTO dto
    ) {
        log.info("PUT /api/pagos/{}", id);
        return ResponseEntity.ok(service.actualizarPago(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar pago",
        description = "Remueve un registro de pago permanentemente del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pago eliminado correctamente",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "Pago eliminado correctamente")
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "El ID especificado no corresponde a ningún pago"
        )
    })
    public ResponseEntity<String> eliminarPago(
            @Parameter(
                description = "ID del pago que se desea eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {
        log.info("DELETE /api/pagos/{}", id);
        service.eliminarPago(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}