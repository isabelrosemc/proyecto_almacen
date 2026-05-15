package com.almacen.ms_detalles_ventas.repository;

import com.almacen.ms_detalles_ventas.model.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository
        extends JpaRepository<DetalleVenta, Long> {
}