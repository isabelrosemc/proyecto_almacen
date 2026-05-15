package com.almacen.ms_detalles_ventas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class DetalleVentaRequestDTO {

    @NotNull(message = "La venta es obligatoria")
    private Long ventaId;

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "Cantidad minima 1")
    private Integer cantidad;
}