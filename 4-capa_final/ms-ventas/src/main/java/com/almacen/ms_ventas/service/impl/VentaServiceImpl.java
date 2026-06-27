package com.almacen.ms_ventas.service.impl;

import com.almacen.ms_ventas.client.*;
import com.almacen.ms_ventas.dto.*;
import com.almacen.ms_ventas.exception.*;
import com.almacen.ms_ventas.mapper.VentaMapper;
import com.almacen.ms_ventas.model.*;
import com.almacen.ms_ventas.repository.VentaRepository;
import com.almacen.ms_ventas.service.VentaService;

import feign.FeignException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaServiceImpl
        implements VentaService {

    private final VentaRepository ventaRepository;

    private final ClienteFeignClient clienteFeignClient;

    private final ProductoFeignClient productoFeignClient;

    private final StockFeignClient stockFeignClient;

    @Override
    @Transactional
    public VentaResponseDTO crearVenta(
            VentaRequestDTO request
    ) {

        log.info("Creando venta");

        ClienteDTO cliente;

        try {

            cliente =
                    clienteFeignClient
                            .obtenerCliente(
                                    request.getClienteId()
                            );

        } catch (FeignException.NotFound ex) {

            throw new RemoteServiceException(
                    "Cliente no encontrado"
            );
        }

        List<DetalleVenta> detallesEntidad =
                new ArrayList<>();

        List<DetalleVentaResponseDTO> detallesDTO =
                new ArrayList<>();

        BigDecimal total =
                BigDecimal.ZERO;

        Venta venta = Venta.builder()
                .clienteId(request.getClienteId())
                .estado(true)
                .build();

        for (DetalleVentaRequestDTO detalle :
                request.getDetalles()) {

            ProductoDTO producto;

            try {

                producto =
                        productoFeignClient
                                .obtenerProducto(
                                        detalle.getProductoId()
                                );

            } catch (FeignException.NotFound ex) {

                throw new RemoteServiceException(
                        "Producto no encontrado"
                );
            }

            StockDTO stock =
                    stockFeignClient
                            .obtenerStockPorProducto(
                                    detalle.getProductoId()
                            );

            if (stock.getStockActual()
                    < detalle.getCantidad()) {

                throw new StockInsuficienteException(
                        "Stock insuficiente para producto ID: "
                                + detalle.getProductoId()
                );
            }

            BigDecimal precioUnitario =
                    BigDecimal.valueOf(
                            producto.getPrecio()
                    );

            BigDecimal subtotal =
                    precioUnitario.multiply(
                            BigDecimal.valueOf(
                                    detalle.getCantidad()
                            )
                    );

            total = total.add(subtotal);

            DetalleVenta detalleEntidad =
                    DetalleVenta.builder()
                            .productoId(
                                    detalle.getProductoId()
                            )
                            .cantidad(
                                    detalle.getCantidad()
                            )
                            .precioUnitario(
                                    precioUnitario
                            )
                            .subtotal(subtotal)
                            .venta(venta)
                            .build();

            detallesEntidad.add(detalleEntidad);

            detallesDTO.add(
                    DetalleVentaResponseDTO
                            .builder()
                            .producto(producto)
                            .cantidad(
                                    detalle.getCantidad()
                            )
                            .precioUnitario(
                                    precioUnitario
                            )
                            .subtotal(subtotal)
                            .build()
            );
        }

        venta.setDetalles(detallesEntidad);
        venta.setTotal(total);

        Venta guardada =
                ventaRepository.save(venta);

        for (DetalleVenta detalle :
                detallesEntidad) {

            stockFeignClient.descontarStock(
                    DescontarStockDTO.builder()
                            .productoId(
                                    detalle.getProductoId()
                            )
                            .cantidad(
                                    detalle.getCantidad()
                            )
                            .build()
            );
        }

        log.info("Venta creada correctamente ID: {}",
                guardada.getId());

        return VentaMapper.toDTO(
                guardada,
                cliente,
                detallesDTO
        );
    }

    @Override
    public List<VentaResponseDTO> listarVentas() {

        log.info("Listando ventas");

        return ventaRepository.findAll()
                .stream()
                .map(venta -> {

                    ClienteDTO cliente =
                            clienteFeignClient
                                    .obtenerCliente(
                                            venta.getClienteId()
                                    );

                    List<DetalleVentaResponseDTO>
                            detallesDTO =
                            venta.getDetalles()
                                    .stream()
                                    .map(detalle -> {

                                        ProductoDTO producto =
                                                productoFeignClient
                                                        .obtenerProducto(
                                                                detalle.getProductoId()
                                                        );

                                        return
                                                DetalleVentaResponseDTO
                                                        .builder()
                                                        .producto(producto)
                                                        .cantidad(
                                                                detalle.getCantidad()
                                                        )
                                                        .precioUnitario(
                                                                detalle.getPrecioUnitario()
                                                        )
                                                        .subtotal(
                                                                detalle.getSubtotal()
                                                        )
                                                        .build();
                                    })
                                    .collect(Collectors.toList());

                    return VentaMapper.toDTO(
                            venta,
                            cliente,
                            detallesDTO
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public VentaResponseDTO buscarPorId(Long id) {

        log.info("Buscando venta ID: {}", id);

        Venta venta =
                ventaRepository.findById(id)
                        .orElseThrow(() ->
                                new VentaNotFoundException(
                                        "Venta no encontrada"
                                ));

        ClienteDTO cliente =
                clienteFeignClient
                        .obtenerCliente(
                                venta.getClienteId()
                        );

        List<DetalleVentaResponseDTO>
                detallesDTO =
                venta.getDetalles()
                        .stream()
                        .map(detalle -> {

                            ProductoDTO producto =
                                    productoFeignClient
                                            .obtenerProducto(
                                                    detalle.getProductoId()
                                            );

                            return
                                    DetalleVentaResponseDTO
                                            .builder()
                                            .producto(producto)
                                            .cantidad(
                                                    detalle.getCantidad()
                                            )
                                            .precioUnitario(
                                                    detalle.getPrecioUnitario()
                                            )
                                            .subtotal(
                                                    detalle.getSubtotal()
                                            )
                                            .build();
                        })
                        .collect(Collectors.toList());

        return VentaMapper.toDTO(
                venta,
                cliente,
                detallesDTO
        );
    }
}