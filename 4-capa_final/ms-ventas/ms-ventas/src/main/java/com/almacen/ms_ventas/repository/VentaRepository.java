package com.almacen.ms_ventas.repository;

import com.almacen.ms_ventas.model.Venta;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository
        extends JpaRepository<Venta, Long> {
}