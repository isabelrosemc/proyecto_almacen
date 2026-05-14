package com.almacen.ms_compras.repository;

import com.almacen.ms_compras.model.Compra;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository
        extends JpaRepository<Compra, Long> {
}