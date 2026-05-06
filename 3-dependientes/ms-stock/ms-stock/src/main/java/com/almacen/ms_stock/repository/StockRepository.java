package com.almacen.ms_stock.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.almacen.ms_stock.model.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    // Método vital para buscar el stock de un producto específico
    Optional<Stock> findByIdProducto(Long idProducto);
}