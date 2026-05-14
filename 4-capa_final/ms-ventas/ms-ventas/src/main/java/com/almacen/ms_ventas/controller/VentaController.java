package com.almacen.ms_ventas.controller;

import com.almacen.ms_ventas.dto.*;
import com.almacen.ms_ventas.service.VentaService;

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
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO>
    crearVenta(
            @Valid @RequestBody
            VentaRequestDTO request
    ) {

        log.info("POST /api/ventas");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ventaService.crearVenta(
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>>
    listarVentas() {

        log.info("GET /api/ventas");

        return ResponseEntity.ok(
                ventaService.listarVentas()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO>
    buscarPorId(@PathVariable Long id) {

        log.info("GET /api/ventas/{}", id);

        return ResponseEntity.ok(
                ventaService.buscarPorId(id)
        );
    }
}