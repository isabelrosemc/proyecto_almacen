package com.almacen.ms_productos.controller;

import com.almacen.ms_productos.dto.*;
import com.almacen.ms_productos.service.ProductoService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponseDTO>
    crearProducto(
            @Valid @RequestBody
            ProductoRequestDTO request
    ) {

        log.info("POST /api/productos");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        productoService
                                .crearProducto(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>>
    listarProductos() {

        log.info("GET /api/productos");

        return ResponseEntity.ok(
                productoService.listarProductos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO>
    buscarPorId(@PathVariable Long id) {

        log.info("GET /api/productos/{}", id);

        return ResponseEntity.ok(
                productoService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO>
    actualizarProducto(
            @PathVariable Long id,

            @Valid @RequestBody
            ProductoRequestDTO request
    ) {

        log.info("PUT /api/productos/{}", id);

        return ResponseEntity.ok(
                productoService.actualizarProducto(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminarProducto(@PathVariable Long id) {

        log.info("DELETE /api/productos/{}", id);

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}