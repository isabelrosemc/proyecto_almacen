package com.almacen.ms_pagos.service.impl;

import com.almacen.ms_pagos.dto.PagoDTO;
import com.almacen.ms_pagos.exception.NotFoundException;
import com.almacen.ms_pagos.mapper.PagoMapper;
import com.almacen.ms_pagos.model.Pago;
import com.almacen.ms_pagos.repository.PagoRepository;
import com.almacen.ms_pagos.service.PagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    @Override
    public List<PagoDTO> listarPagos() {
        return pagoRepository.findAll()
                .stream()
                .map(PagoMapper::toDTO)
                .toList();
    }

    @Override
    public PagoDTO buscarPago(Long id) {

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        return PagoMapper.toDTO(pago);
    }

    @Override
    public PagoDTO guardarPago(PagoDTO dto) {

        if (pagoRepository.existsByIdVenta(dto.getIdVenta())) {
            throw new RuntimeException("Ya existe pago para esta venta");
        }

        Pago pago = PagoMapper.toEntity(dto);

        return PagoMapper.toDTO(pagoRepository.save(pago));
    }

    @Override
    public PagoDTO actualizarPago(Long id, PagoDTO dto) {

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setIdVenta(dto.getIdVenta());

        return PagoMapper.toDTO(pagoRepository.save(pago));
    }

    @Override
    public void eliminarPago(Long id) {

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado"));

        pagoRepository.delete(pago);
    }
}