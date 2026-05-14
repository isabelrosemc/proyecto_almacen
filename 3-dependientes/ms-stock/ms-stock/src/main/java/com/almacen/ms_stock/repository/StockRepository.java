package com.almacen.ms_stock.repository;

import com.almacen.ms_stock.model.Stock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository
        extends JpaRepository<Stock, Long> {

    boolean existsByProductoId(Long productoId);

    Optional<Stock> findByProductoId(Long productoId);
}