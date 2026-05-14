package com.almacen.ms_stock.controller;

import com.almacen.ms_stock.dto.*;
import com.almacen.ms_stock.service.StockService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<StockResponseDTO>
    crearStock(
            @Valid @RequestBody
            StockRequestDTO request
    ) {

        log.info("POST /api/stock");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        stockService.crearStock(request)
                );
    }

    @GetMapping
    public ResponseEntity<List<StockResponseDTO>>
    listarStock() {

        log.info("GET /api/stock");

        return ResponseEntity.ok(
                stockService.listarStock()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponseDTO>
    buscarPorId(@PathVariable Long id) {

        log.info("GET /api/stock/{}", id);

        return ResponseEntity.ok(
                stockService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockResponseDTO>
    actualizarStock(
            @PathVariable Long id,

            @Valid @RequestBody
            StockRequestDTO request
    ) {

        log.info("PUT /api/stock/{}", id);

        return ResponseEntity.ok(
                stockService.actualizarStock(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    eliminarStock(@PathVariable Long id) {

        log.info("DELETE /api/stock/{}", id);

        stockService.eliminarStock(id);

        return ResponseEntity.noContent().build();
    }
}