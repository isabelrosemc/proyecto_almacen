package com.almacen.ms_productos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_productos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    Optional<Producto> findByProductoId(Long productoId);
    
}