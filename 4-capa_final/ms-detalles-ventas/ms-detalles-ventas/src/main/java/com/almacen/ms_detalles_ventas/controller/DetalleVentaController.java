package com.almacen.ms_detalles_ventas.controller;

import com.almacen.ms_detalles_ventas.dto.*;
import com.almacen.ms_detalles_ventas.service.DetalleVentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-ventas")
@RequiredArgsConstructor
@Slf4j
public class DetalleVentaController {

    private final DetalleVentaService service;

    @PostMapping
    public ResponseEntity<DetalleVentaResponseDTO>
    crear(@Valid @RequestBody
           DetalleVentaRequestDTO dto) {

        log.info("POST detalle ventas");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(dto));
    }

    @GetMapping
    public ResponseEntity<List<DetalleVentaResponseDTO>>
    listar() {

        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVentaResponseDTO>
    buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminar(@PathVariable Long id) {

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}