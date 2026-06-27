package com.almacen.ms_compras.service.impl;

import com.almacen.ms_compras.client.*;
import com.almacen.ms_compras.dto.*;
import com.almacen.ms_compras.exception.*;
import com.almacen.ms_compras.mapper.CompraMapper;
import com.almacen.ms_compras.model.*;
import com.almacen.ms_compras.repository.CompraRepository;
import com.almacen.ms_compras.service.CompraService;

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
public class CompraServiceImpl
        implements CompraService {

    private final CompraRepository compraRepository;

    private final ProveedorFeignClient proveedorFeignClient;

    private final ProductoFeignClient productoFeignClient;

    private final StockFeignClient stockFeignClient;

    @Override
    @Transactional
    public CompraResponseDTO crearCompra(
            CompraRequestDTO request
    ) {

        log.info("Creando compra");

        ProveedorDTO proveedor;

        try {

            proveedor =
                    proveedorFeignClient
                            .obtenerProveedor(
                                    request.getProveedorId()
                            );

        } catch (FeignException.NotFound ex) {

            throw new RemoteServiceException(
                    "Proveedor no encontrado"
            );
        }

        List<DetalleCompra> detallesEntidad =
                new ArrayList<>();

        List<DetalleCompraResponseDTO> detallesDTO =
                new ArrayList<>();

        BigDecimal total =
                BigDecimal.ZERO;

        Compra compra = Compra.builder()
                .proveedorId(request.getProveedorId())
                .estado(true)
                .build();

        for (DetalleCompraRequestDTO detalle :
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

            DetalleCompra detalleEntidad =
                    DetalleCompra.builder()
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
                            .compra(compra)
                            .build();

            detallesEntidad.add(detalleEntidad);

            detallesDTO.add(
                    DetalleCompraResponseDTO
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

        compra.setDetalles(detallesEntidad);
        compra.setTotal(total);

        Compra guardada =
                compraRepository.save(compra);

        for (DetalleCompra detalle :
                detallesEntidad) {

            stockFeignClient.ingresarStock(
                    ActualizarStockDTO.builder()
                            .productoId(
                                    detalle.getProductoId()
                            )
                            .cantidad(
                                    detalle.getCantidad()
                            )
                            .build()
            );
        }

        log.info("Compra creada correctamente ID: {}",
                guardada.getId());

        return CompraMapper.toDTO(
                guardada,
                proveedor,
                detallesDTO
        );
    }

    @Override
    public List<CompraResponseDTO> listarCompras() {

        log.info("Listando compras");

        return compraRepository.findAll()
                .stream()
                .map(compra -> {

                    ProveedorDTO proveedor =
                            proveedorFeignClient
                                    .obtenerProveedor(
                                            compra.getProveedorId()
                                    );

                    List<DetalleCompraResponseDTO>
                            detallesDTO =
                            compra.getDetalles()
                                    .stream()
                                    .map(detalle -> {

                                        ProductoDTO producto =
                                                productoFeignClient
                                                        .obtenerProducto(
                                                                detalle.getProductoId()
                                                        );

                                        return
                                                DetalleCompraResponseDTO
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

                    return CompraMapper.toDTO(
                            compra,
                            proveedor,
                            detallesDTO
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public CompraResponseDTO buscarPorId(Long id) {

        log.info("Buscando compra ID: {}", id);

        Compra compra =
                compraRepository.findById(id)
                        .orElseThrow(() ->
                                new CompraNotFoundException(
                                        "Compra no encontrada"
                                ));

        ProveedorDTO proveedor =
                proveedorFeignClient
                        .obtenerProveedor(
                                compra.getProveedorId()
                        );

        List<DetalleCompraResponseDTO>
                detallesDTO =
                compra.getDetalles()
                        .stream()
                        .map(detalle -> {

                            ProductoDTO producto =
                                    productoFeignClient
                                            .obtenerProducto(
                                                    detalle.getProductoId()
                                            );

                            return
                                    DetalleCompraResponseDTO
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

        return CompraMapper.toDTO(
                compra,
                proveedor,
                detallesDTO
        );
    }
}