package com.almacen.ms_pagos.controller;

import com.almacen.ms_pagos.dto.PagoDTO;
import com.almacen.ms_pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarPagos() {
        return ResponseEntity.ok(service.listarPagos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscarPago(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPago(id));
    }

    @PostMapping
    public ResponseEntity<PagoDTO> guardarPago(@RequestBody PagoDTO dto) {
        return ResponseEntity.ok(service.guardarPago(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizarPago(
            @PathVariable Long id,
            @RequestBody PagoDTO dto
    ) {
        return ResponseEntity.ok(service.actualizarPago(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPago(@PathVariable Long id) {
        service.eliminarPago(id);
        return ResponseEntity.ok("Pago eliminado correctamente");
    }
}