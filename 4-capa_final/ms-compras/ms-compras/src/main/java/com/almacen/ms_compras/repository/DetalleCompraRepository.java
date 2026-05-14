package com.almacen.ms_compras.repository;

import com.almacen.ms_compras.model.DetalleCompra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCompraRepository
        extends JpaRepository<DetalleCompra, Long> {
}