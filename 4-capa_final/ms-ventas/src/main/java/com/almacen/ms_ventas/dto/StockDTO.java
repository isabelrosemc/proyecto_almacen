package com.almacen.ms_ventas.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockDTO {

    private Long id;

    private Integer stockActual;
}