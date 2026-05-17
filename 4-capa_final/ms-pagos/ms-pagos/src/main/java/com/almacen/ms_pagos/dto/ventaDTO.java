package com.almacen.ms_pagos.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ventaDTO {

    private Long idVenta;

    private Double total;

    private String estado;
}