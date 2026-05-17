package com.almacen.ms_reportes.controller;

import com.almacen.ms_reportes.dto.VentaResponseDTO;
import com.almacen.ms_reportes.service.ReporteService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas")
    public ResponseEntity<List<VentaResponseDTO>> obtenerReporteVentas() {

        return ResponseEntity.ok(
                reporteService.obtenerReporteVentas()
        );
    }
}