package com.almacen.ms_ventas.mapper;

import com.almacen.ms_ventas.dto.*;
import com.almacen.ms_ventas.model.Venta;

import java.util.List;

public class VentaMapper {

    public static VentaResponseDTO toDTO(
            Venta venta,
            ClienteDTO cliente,
            List<DetalleVentaResponseDTO> detalles
    ) {

        return VentaResponseDTO.builder()
                .id(venta.getId())
                .cliente(cliente)
                .detalles(detalles)
                .total(venta.getTotal())
                .estado(venta.getEstado())
                .fechaVenta(venta.getFechaVenta())
                .build();
    }
}