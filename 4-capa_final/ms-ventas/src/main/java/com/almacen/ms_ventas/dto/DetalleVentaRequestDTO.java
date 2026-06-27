package com.almacen.ms_ventas.dto;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaRequestDTO {

    @NotNull(message = "Producto obligatorio")
    private Long productoId;

    @Min(value = 1,
            message = "Cantidad invalida")
    private Integer cantidad;
}