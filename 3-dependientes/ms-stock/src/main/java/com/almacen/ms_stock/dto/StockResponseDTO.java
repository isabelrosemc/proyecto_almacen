package com.almacen.ms_stock.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockResponseDTO {

    private Long id;

    private ProductoDTO producto;

    private Integer stockActual;

    private Integer stockMinimo;

    private Integer stockMaximo;

    private Boolean estado;

    private LocalDateTime fechaCreacion;

    private String mensaje;
}