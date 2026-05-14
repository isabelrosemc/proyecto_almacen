package com.almacen.ms_ventas.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDTO {

    private Long id;

    private ClienteDTO cliente;

    private List<DetalleVentaResponseDTO> detalles;

    private BigDecimal total;

    private Boolean estado;

    private LocalDateTime fechaVenta;
}