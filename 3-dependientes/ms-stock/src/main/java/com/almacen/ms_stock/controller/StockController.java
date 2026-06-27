package com.almacen.ms_stock.controller;

import com.almacen.ms_stock.dto.*;
import com.almacen.ms_stock.service.StockService;

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
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@Slf4j
@Tag(
    name = "Stock",
    description = "Operaciones relacionadas con la gestión de stock de productos"
)
public class StockController {

    private final StockService stockService;

    @PostMapping
    @Operation(
        summary = "Crear registro de stock",
        description = "Registra el stock inicial de un producto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Stock creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StockResponseDTO.class),
                examples = @ExampleObject(
                    name = "Stock Creado",
                    value = """
                    {
                      "id": 1,
                      "productoId": 1,
                      "cantidad": 100
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
    public ResponseEntity<StockResponseDTO> crearStock(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos necesarios para crear el registro de stock",
                required = true
            )
            @Valid @RequestBody StockRequestDTO request
    ) {

        log.info("POST /api/stock");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        stockService.crearStock(request)
                );
    }

    @GetMapping
    @Operation(
        summary = "Listar stock",
        description = "Obtiene todos los registros de stock almacenados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Listado obtenido correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StockResponseDTO.class)
            )
        )
    })
    public ResponseEntity<List<StockResponseDTO>> listarStock() {

        log.info("GET /api/stock");

        return ResponseEntity.ok(
                stockService.listarStock()
        );
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar stock por ID",
        description = "Obtiene un registro de stock mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Registro encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StockResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Registro no encontrado"
        )
    })
    public ResponseEntity<StockResponseDTO> buscarPorId(
            @Parameter(
                description = "ID del registro de stock",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("GET /api/stock/{}", id);

        return ResponseEntity.ok(
                stockService.buscarPorId(id)
        );
    }

    @GetMapping("/producto/{productoId}")
    @Operation(
        summary = "Buscar stock por producto",
        description = "Obtiene el stock asociado a un producto específico"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StockResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No existe stock asociado al producto"
        )
    })
    public ResponseEntity<StockResponseDTO> buscarPorProductoId(
            @Parameter(
                description = "ID del producto",
                required = true,
                example = "1"
            )
            @PathVariable Long productoId
    ) {

        log.info("GET /api/stock/producto/{}", productoId);

        return ResponseEntity.ok(
                stockService.buscarPorProductoId(
                        productoId
                )
        );
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar stock",
        description = "Actualiza la información de un registro de stock existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StockResponseDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Registro no encontrado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<StockResponseDTO> actualizarStock(
            @Parameter(
                description = "ID del registro de stock",
                required = true,
                example = "1"
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos actualizados del stock",
                required = true
            )
            @Valid @RequestBody StockRequestDTO request
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
    @Operation(
        summary = "Eliminar stock",
        description = "Elimina un registro de stock mediante su identificador"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Registro eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Registro no encontrado"
        )
    })
    public ResponseEntity<Void> eliminarStock(
            @Parameter(
                description = "ID del registro a eliminar",
                required = true,
                example = "1"
            )
            @PathVariable Long id
    ) {

        log.info("DELETE /api/stock/{}", id);

        stockService.eliminarStock(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ingresar")
    @Operation(
        summary = "Ingresar stock",
        description = "Incrementa la cantidad disponible de stock para un producto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock ingresado correctamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto o stock no encontrado"
        )
    })
    public ResponseEntity<Void> ingresarStock(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos para incrementar el stock",
                required = true
            )
            @RequestBody ActualizarStockDTO request

    ) {

        log.info("PUT /api/stock/ingresar");

        stockService.ingresarStock(request);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/descontar")
    @Operation(
        summary = "Descontar stock",
        description = "Reduce la cantidad disponible de stock para un producto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock descontado correctamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Cantidad inválida o stock insuficiente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto o stock no encontrado"
        )
    })
    public ResponseEntity<Void> descontarStock(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos para descontar stock",
                required = true
            )
            @RequestBody ActualizarStockDTO request

    ) {

        log.info("PUT /api/stock/descontar");

        stockService.descontarStock(request);

        return ResponseEntity.ok().build();
    }
}