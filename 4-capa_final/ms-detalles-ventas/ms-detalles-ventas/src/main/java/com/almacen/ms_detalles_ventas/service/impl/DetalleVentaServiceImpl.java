package com.almacen.ms_detalles_ventas.service.impl;

import com.almacen.ms_detalles_ventas.client.*;
import com.almacen.ms_detalles_ventas.dto.*;
import com.almacen.ms_detalles_ventas.exception.*;
import com.almacen.ms_detalles_ventas.mapper.DetalleVentaMapper;
import com.almacen.ms_detalles_ventas.model.DetalleVenta;
import com.almacen.ms_detalles_ventas.repository.DetalleVentaRepository;
import com.almacen.ms_detalles_ventas.service.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetalleVentaServiceImpl
        implements DetalleVentaService {

    private final DetalleVentaRepository repository;
    private final ProductoClient productoClient;
    private final VentaClient ventaClient;

    @Override
    public DetalleVentaResponseDTO crear(
            DetalleVentaRequestDTO dto) {

        log.info("Creando detalle venta");

        ventaClient.obtenerVenta(dto.getVentaId());

        productoClient.obtenerProducto(dto.getProductoId());

        BigDecimal precio = BigDecimal.valueOf(100);

        BigDecimal subtotal =
                precio.multiply(
                        BigDecimal.valueOf(dto.getCantidad())
                );

        DetalleVenta detalle = DetalleVenta.builder()
                .ventaId(dto.getVentaId())
                .productoId(dto.getProductoId())
                .cantidad(dto.getCantidad())
                .precioUnitario(precio)
                .subtotal(subtotal)
                .build();

        return DetalleVentaMapper.toDTO(
                repository.save(detalle)
        );
    }

    @Override
    public List<DetalleVentaResponseDTO> listar() {

        log.info("Listando detalles ventas");

        return repository.findAll()
                .stream()
                .map(DetalleVentaMapper::toDTO)
                .toList();
    }

    @Override
    public DetalleVentaResponseDTO buscarPorId(Long id) {

        log.info("Buscando detalle ID {}", id);

        DetalleVenta detalle = repository.findById(id)
                .orElseThrow(() ->
                        new DetalleVentaNotFoundException(
                                "Detalle no encontrado"
                        )
                );

        return DetalleVentaMapper.toDTO(detalle);
    }

    @Override
    public void eliminar(Long id) {

        log.info("Eliminando detalle ID {}", id);

        DetalleVenta detalle = repository.findById(id)
                .orElseThrow(() ->
                        new DetalleVentaNotFoundException(
                                "Detalle no encontrado"
                        )
                );

        repository.delete(detalle);
    }
}