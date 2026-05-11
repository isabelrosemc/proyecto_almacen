package com.almacen.ms_proveedores.controller;


import com.ms.proveedores.dto.*;
import com.ms.proveedores.service.ProveedorService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
@Slf4j
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponseDTO>
    crearProveedor(
            @Valid @RequestBody
            ProveedorRequestDTO request
    ) {

        log.info("POST /api/proveedores");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proveedorService.crearProveedor(request));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponseDTO>>
    listarProveedores() {

        log.info("GET /api/proveedores");

        return ResponseEntity.ok(
                proveedorService.listarProveedores()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO>
    buscarPorId(@PathVariable Long id) {

        log.info("GET /api/proveedores/{}", id);

        return ResponseEntity.ok(
                proveedorService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponseDTO>
    actualizarProveedor(
            @PathVariable Long id,
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
    public ResponseEntity<Void>
    eliminarProveedor(@PathVariable Long id) {

        log.info("DELETE /api/proveedores/{}", id);

        proveedorService.eliminarProveedor(id);

        return ResponseEntity.noContent().build();
    }
}