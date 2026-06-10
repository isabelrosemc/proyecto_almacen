package com.almacen.ms_productos.controller;

import com.almacen.ms_productos.dto.*;
import com.almacen.ms_productos.service.ProductoService;

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

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Productos",
    description = "Operaciones relacionadas con la gestión y el catálogo de productos"
)
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en el catálogo del sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Producto creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductoResponseDTO.class),
                examples = @ExampleObject(
                    name = "Producto Creado",
                    value = """
                    {
                      "id": 1,
                      "nombre": "Teclado Mecánico RGB",
                      "descripcion": "Teclado con switches red y retroiluminación",
                      "precio": 89.99,
                      "stock": 50,
                      "estado": true
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos"
        )
    })
    public ResponseEntity<ProductoResponseDTO> crearProducto(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del producto a registrar",
                required = true
            )
            @Valid @RequestBody ProductoRequestDTO request
    ) {

        log.info("POST /api/productos");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productoService.crearProducto(request));
    }

    @GetMapping
    @Operation(
        summary = "Listar productos",
        description = "Obtiene todos los productos registrados en el catálogo"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado de productos obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductoResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<ProductoResponseDTO>> listarProductos() {

        log.info("GET /api/productos");

        return ResponseEntity.ok(
                productoService.listarProductos()
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar producto por ID",
        description = "Obtiene los detalles de un producto específico mediante su identificador único"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado con éxito",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    public ResponseEntity<ProductoResponseDTO> buscarPorId(
            @Parameter(
                description = "ID del producto a buscar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("GET /api/productos/{}", id);

        return ResponseEntity.ok(
                productoService.buscarPorId(id)
        );
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar producto",
        description = "Actualiza los datos de un producto existente en base a su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductoResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos provistos inválidos"
        )
    })
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(
            @Parameter(
                description = "ID del producto a actualizar",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevos datos para actualizar el producto",
                required = true
            )
            @Valid @RequestBody ProductoRequestDTO request
    ) {

        log.info("PUT /api/productos/{}", id);

        return ResponseEntity.ok(
                productoService.actualizarProducto(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar producto",
        description = "Remueve de forma lógica o física un producto utilizando su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Producto eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    public ResponseEntity<Void> eliminarProducto(
            @Parameter(
                description = "ID del producto a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("DELETE /api/productos/{}", id);

        productoService.eliminarProducto(id);

        return ResponseEntity.noContent().build();
    }
}