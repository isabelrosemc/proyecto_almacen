package com.almacen.ms_compras.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompraResponseDTO {

    private Long id;

    private ProveedorDTO proveedor;

    private List<DetalleCompraResponseDTO> detalles;

    private BigDecimal total;

    private Boolean estado;

    private LocalDateTime fechaCompra;
}