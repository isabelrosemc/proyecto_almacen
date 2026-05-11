package com.almacen.ms_productos.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    private String descripcion;

    @NotBlank(message = "El SKU es obligatorio")
    private String sku;

    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

    @NotNull(message = "La categoria es obligatoria")
    private Long categoriaId;

    @NotNull(message = "El proveedor es obligatorio")
    private Long proveedorId;

    private Boolean estado;
}