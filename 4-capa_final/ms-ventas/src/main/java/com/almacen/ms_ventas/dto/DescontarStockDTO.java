package com.almacen.ms_ventas.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescontarStockDTO {

    private Long productoId;

    private Integer cantidad;
}