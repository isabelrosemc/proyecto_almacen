package com.almacen.ms_pagos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDTO {

    private Double monto;
    private String metodoPago;
    private String estadoPago;
    private Long idVenta;
}
