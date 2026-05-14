package com.almacen.ms_compras.mapper;

import com.almacen.ms_compras.dto.*;
import com.almacen.ms_compras.model.*;

import java.util.List;

public class CompraMapper {

    public static CompraResponseDTO toDTO(
            Compra compra,
            ProveedorDTO proveedor,
            List<DetalleCompraResponseDTO> detalles
    ) {

        return CompraResponseDTO.builder()
                .id(compra.getId())
                .proveedor(proveedor)
                .detalles(detalles)
                .total(compra.getTotal())
                .estado(compra.getEstado())
                .fechaCompra(compra.getFechaCompra())
                .build();
    }
}