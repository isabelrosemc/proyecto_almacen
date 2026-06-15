package com.almacen.ms_reportes.controller;

import com.almacen.ms_reportes.dto.VentaResponseDTO;
import com.almacen.ms_reportes.service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Reportes",
    description = "Operaciones relacionadas con la generación de reportes y estadísticas"
)
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas")
    @Operation(
        summary = "Obtener reporte de ventas",
        description = "Consolida y extrae el listado histórico de las ventas procesadas para análisis comercial"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Reporte de ventas generado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = VentaResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno al procesar o consolidar los datos del reporte"
        )
    })
    public ResponseEntity<List<VentaResponseDTO>> obtenerReporteVentas() {

        log.info("GET /api/reportes/ventas");

        return ResponseEntity.ok(
                reporteService.obtenerReporteVentas()
        );
    }
}