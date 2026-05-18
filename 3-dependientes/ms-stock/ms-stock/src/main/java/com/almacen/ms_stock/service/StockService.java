package com.almacen.ms_stock.service;

import com.almacen.ms_stock.dto.*;

import java.util.List;

public interface StockService {

    StockResponseDTO crearStock(
            StockRequestDTO request
    );

    List<StockResponseDTO> listarStock();

    StockResponseDTO buscarPorId(Long id);

    StockResponseDTO actualizarStock(
            Long id,
            StockRequestDTO request
    );

    void eliminarStock(Long id);

    void ingresarStock(ActualizarStockDTO request);

    void descontarStock(ActualizarStockDTO request);

    StockResponseDTO buscarPorProductoId(Long productoId);
}