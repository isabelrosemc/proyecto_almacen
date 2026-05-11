package com.almacen.ms_productos.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponseDTO {

    private Long id;

    private String nombre;

    private String descripcion;

    private String sku;

    private BigDecimal precio;

    private CategoriaDTO categoria;

    private ProveedorDTO proveedor;

    private Boolean estado;

    private LocalDateTime fechaCreacion;
}