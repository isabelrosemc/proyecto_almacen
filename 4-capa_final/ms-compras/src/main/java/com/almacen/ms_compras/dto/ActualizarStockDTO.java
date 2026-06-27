package com.almacen.ms_compras.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActualizarStockDTO {

    private Long productoId;

    private Integer cantidad;
}