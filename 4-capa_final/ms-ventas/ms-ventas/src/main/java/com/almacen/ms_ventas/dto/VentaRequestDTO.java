package com.almacen.ms_ventas.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaRequestDTO {

    @NotNull(message = "Cliente obligatorio")
    private Long clienteId;

    @NotEmpty(message = "Detalles obligatorios")
    @Valid
    private List<DetalleVentaRequestDTO> detalles;
}