package com.almacen.ms_pagos.mapper;

import com.almacen.ms_pagos.dto.PagoDTO;
import com.almacen.ms_pagos.model.Pago;

import java.time.LocalDate;

public class PagoMapper {

    public static PagoDTO toDTO(Pago pago) {
        return new PagoDTO(
                pago.getMonto(),
                pago.getMetodoPago(),
                pago.getEstadoPago(),
                pago.getIdVenta()
        );
    }

    public static Pago toEntity(PagoDTO dto) {
        Pago pago = new Pago();

        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setIdVenta(dto.getIdVenta());
        pago.setFechaPago(LocalDate.now());

        return pago;
    }
}