package com.almacen.ms_stock.mapper;

import com.almacen.ms_stock.dto.*;
import com.almacen.ms_stock.model.Stock;

public class StockMapper {

    public static Stock toEntity(
            StockRequestDTO dto
    ) {

        return Stock.builder()
                .productoId(dto.getProductoId())
                .stockActual(dto.getStockActual())
                .stockMinimo(dto.getStockMinimo())
                .stockMaximo(dto.getStockMaximo())
                .estado(dto.getEstado())
                .build();
    }

    public static StockResponseDTO toDTO(
            Stock stock,
            ProductoDTO producto
    ) {

        return StockResponseDTO.builder()
                .id(stock.getId())
                .producto(producto)
                .stockActual(stock.getStockActual())
                .stockMinimo(stock.getStockMinimo())
                .stockMaximo(stock.getStockMaximo())
                .estado(stock.getEstado())
                .fechaCreacion(stock.getFechaCreacion())
                .build();
    }
}