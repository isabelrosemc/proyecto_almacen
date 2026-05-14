package com.almacen.ms_compras.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDTO {

    private Long id;

    private String razonSocial;
}