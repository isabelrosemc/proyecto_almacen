package com.almacen.ms_categoria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.almacen.ms_categoria.dto.CategoriaRequestDTO;
import com.almacen.ms_categoria.dto.CategoriaResponseDTO;
import com.almacen.ms_categoria.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Slf4j
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @Valid @RequestBody CategoriaRequestDTO request
    ) {

        log.info("POST /api/categorias ejecutado");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.crearCategoria(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {

        log.info("GET /api/categorias ejecutado");

        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id) {

        log.info("GET /api/categorias/{} ejecutado", id);

        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO request
    ) {

        log.info("PUT /api/categorias/{} ejecutado", id);

        return ResponseEntity.ok(categoriaService.actualizarCategoria(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {

        log.info("DELETE /api/categorias/{} ejecutado", id);

        categoriaService.eliminarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}