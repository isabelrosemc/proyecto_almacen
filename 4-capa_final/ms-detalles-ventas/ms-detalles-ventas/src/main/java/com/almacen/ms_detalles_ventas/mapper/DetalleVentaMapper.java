package com.almacen.ms_detalles_ventas.mapper;

import com.almacen.ms_detalles_ventas.dto.DetalleVentaResponseDTO;
import com.almacen.ms_detalles_ventas.model.DetalleVenta;

public class DetalleVentaMapper {

    public static DetalleVentaResponseDTO toDTO(
            DetalleVenta detalle) {

        return DetalleVentaResponseDTO.builder()
                .id(detalle.getId())
                .ventaId(detalle.getVentaId())
                .productoId(detalle.getProductoId())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}