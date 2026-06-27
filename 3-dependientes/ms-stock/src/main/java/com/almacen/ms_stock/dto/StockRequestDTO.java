package com.almacen.ms_stock.dto;

import jakarta.validation.constraints.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockRequestDTO {

    @NotNull(message = "El producto es obligatorio")
    private Long productoId;

    @Min(value = 0,
            message = "Stock actual invalido")
    private Integer stockActual;

    @Min(value = 0,
            message = "Stock minimo invalido")
    private Integer stockMinimo;

    @Min(value = 1,
            message = "Stock maximo invalido")
    private Integer stockMaximo;

    private Boolean estado;
}