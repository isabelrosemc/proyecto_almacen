package com.almacen.ms_productos.repository;

import com.almacen.ms_productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    boolean existsBySku(String sku);
}