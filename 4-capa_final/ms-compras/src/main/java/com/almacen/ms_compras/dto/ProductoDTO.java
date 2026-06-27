package com.almacen.ms_compras.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {

    private Long id;

    private String nombre;

    private String sku;

    private Double precio;
}