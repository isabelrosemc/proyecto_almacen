package com.almacen.ms_stock.service;

import java.util.List;

import com.almacen.ms_stock.model.Stock;

public interface StockService {

    List<Stock> listar();

    Stock buscarPorId(Long id);

    Stock guardar(Stock stock);

    Stock actualizar(Long id, Stock stock);

    void eliminar(Long id);

    Stock buscarPorIdProducto(Long idProducto);
}