package com.almacen.ms_compras.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraRequestDTO {

    @NotNull(message = "Proveedor obligatorio")
    private Long proveedorId;

    @NotEmpty(message = "Detalles obligatorios")
    @Valid
    private List<DetalleCompraRequestDTO> detalles;
}