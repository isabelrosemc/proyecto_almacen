package com.almacen.ms_productos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.almacen.ms_productos.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    List<Producto> findByIdCategoria(Long idCategoria);

}