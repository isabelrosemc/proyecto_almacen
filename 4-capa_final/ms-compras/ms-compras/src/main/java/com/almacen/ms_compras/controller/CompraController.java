package com.almacen.ms_compras.controller;

import com.almacen.ms_compras.dto.*;
import com.almacen.ms_compras.service.CompraService;

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
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<CompraResponseDTO>
    crearCompra(
            @Valid @RequestBody
            CompraRequestDTO request
    ) {

        log.info("POST /api/compras");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        compraService.crearCompra(
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>>
    listarCompras() {

        log.info("GET /api/compras");

        return ResponseEntity.ok(
                compraService.listarCompras()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO>
    buscarPorId(@PathVariable Long id) {

        log.info("GET /api/compras/{}", id);

        return ResponseEntity.ok(
                compraService.buscarPorId(id)
        );
    }
}