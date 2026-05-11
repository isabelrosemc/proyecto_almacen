package com.almacen.ms_productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_productos.model.Producto;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    boolean existsBySku(String sku);
}