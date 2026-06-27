package com.almacen.ms_categoria.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.almacen.ms_categoria.dto.CategoriaRequestDTO;
import com.almacen.ms_categoria.dto.CategoriaResponseDTO;
import com.almacen.ms_categoria.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Categorías",
    description = "Operaciones relacionadas con la gestión de categorías de productos"
)
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @Operation(
        summary = "Crear categoría",
        description = "Registra una nueva categoría en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Categoría creada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaResponseDTO.class),
                examples = @ExampleObject(
                    name = "Categoria Creada",
                    value = """
                    {
                      "id": 1,
                      "nombre": "Electrónica",
                      "descripcion": "Productos electrónicos",
                      "estado": true
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de la categoría a crear",
                required = true
            )
            @Valid @RequestBody CategoriaRequestDTO request
    ) {

        log.info("POST /api/categorias ejecutado");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.crearCategoria(request));
    }

    @GetMapping
    @Operation(
        summary = "Listar categorías",
        description = "Obtiene todas las categorías registradas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias() {

        log.info("GET /api/categorias ejecutado");

        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar categoría por ID",
        description = "Obtiene una categoría específica mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoría encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada"
        )
    })
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @Parameter(
                description = "ID de la categoría",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("GET /api/categorias/{} ejecutado", id);

        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar categoría",
        description = "Actualiza los datos de una categoría existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoría actualizada correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = CategoriaResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(

            @Parameter(
                description = "ID de la categoría a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos de la categoría",
                required = true
            )
            @Valid @RequestBody CategoriaRequestDTO request
    ) {

        log.info("PUT /api/categorias/{} ejecutado", id);

        return ResponseEntity.ok(
                categoriaService.actualizarCategoria(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar categoría",
        description = "Elimina una categoría utilizando su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Categoría eliminada correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Categoría no encontrada"
        )
    })
    public ResponseEntity<Void> eliminarCategoria(

            @Parameter(
                description = "ID de la categoría a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("DELETE /api/categorias/{} ejecutado", id);

        categoriaService.eliminarCategoria(id);

        return ResponseEntity.noContent().build();
    }
}