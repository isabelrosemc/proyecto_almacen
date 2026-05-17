package com.almacen.ms_pagos.repository;

import com.almacen.ms_pagos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    boolean existsByIdVenta(Long idVenta);
}