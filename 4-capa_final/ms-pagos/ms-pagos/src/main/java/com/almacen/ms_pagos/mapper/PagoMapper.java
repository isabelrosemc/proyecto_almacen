package com.example.ms_pagos.mapper;

import com.example.ms_pagos.dto.PagoDTO;
import com.example.ms_pagos.model.Pago;

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

        return pago;
    }
}