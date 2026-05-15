package com.almacen.ms_pagos.service.impl;

import com.almacen.ms_pagos.client.VentaFeignClient;
import com.almacen.ms_pagos.dto.PagoRequestDTO;
import com.almacen.ms_pagos.dto.PagoResponseDTO;
import com.almacen.ms_pagos.dto.VentaDTO;
import com.almacen.ms_pagos.exception.*;
import com.almacen.ms_pagos.mapper.PagoMapper;
import com.almacen.ms_pagos.model.Pago;
import com.almacen.ms_pagos.repository.PagoRepository;
import com.almacen.ms_pagos.service.PagoService;

import feign.FeignException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final VentaFeignClient ventaFeignClient;

    @Override
    public PagoResponseDTO crearPago(PagoRequestDTO request) {

        log.info("Creando pago para venta ID: {}", request.getVentaId());

        boolean existePago =
                pagoRepository.existsByVentaId(request.getVentaId());

        if (existePago) {
            throw new DuplicatePagoException(
                    "La venta ya tiene un pago registrado"
            );
        }

        validarReglasPago(request);

        VentaDTO venta;

        try {
            venta = ventaFeignClient.obtenerVenta(request.getVentaId());
        } catch (FeignException.NotFound ex) {
            throw new RemoteServiceException("Venta no encontrada");
        } catch (FeignException ex) {
            throw new RemoteServiceException("Error al conectar con ms-ventas");
        }

        Pago pago = PagoMapper.toEntity(request);

        Pago guardado = pagoRepository.save(pago);

        log.info("Pago creado correctamente ID: {}", guardado.getIdPago());

        return PagoMapper.toDTO(guardado, venta);
    }

    @Override
    public List<PagoResponseDTO> listarPagos() {

        log.info("Listando pagos");

        return pagoRepository.findAll()
                .stream()
                .map(pago -> {

                    VentaDTO venta = ventaFeignClient.obtenerVenta(
                            pago.getVentaId()
                    );

                    return PagoMapper.toDTO(pago, venta);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PagoResponseDTO buscarPorId(Long id) {

        log.info("Buscando pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() ->
                        new PagoNotFoundException("Pago no encontrado")
                );

        VentaDTO venta = ventaFeignClient.obtenerVenta(
                pago.getVentaId()
        );

        return PagoMapper.toDTO(pago, venta);
    }

    @Override
    public void eliminarPago(Long id) {

        log.info("Eliminando pago ID: {}", id);

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() ->
                        new PagoNotFoundException("Pago no encontrado")
                );

        pagoRepository.delete(pago);

        log.info("Pago eliminado correctamente");
    }

    private void validarReglasPago(PagoRequestDTO request) {

        if (request.getMonto() <= 0) {
            throw new InvalidPagoException(
                    "El monto del pago debe ser mayor a 0"
            );
        }

        if (request.getVentaId() == null) {
            throw new InvalidPagoException(
                    "VentaId no puede ser nulo"
            );
        }
    }
}