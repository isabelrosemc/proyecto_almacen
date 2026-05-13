package com.almacen.ms_productos.service.impl;

import feign.FeignException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.almacen.ms_productos.client.ProveedorClient;
import com.almacen.ms_productos.client.CategoriaClient;
import com.almacen.ms_productos.dto.CategoriaDTO;
import com.almacen.ms_productos.dto.ProductoRequestDTO;
import com.almacen.ms_productos.dto.ProductoResponseDTO;
import com.almacen.ms_productos.dto.ProveedorDTO;
import com.almacen.ms_productos.exception.DuplicateProductoException;
import com.almacen.ms_productos.exception.ProductoNotFoundException;
import com.almacen.ms_productos.exception.RemoteServiceException;
import com.almacen.ms_productos.mapper.ProductoMapper;
import com.almacen.ms_productos.model.Producto;
import com.almacen.ms_productos.repository.ProductoRepository;
import com.almacen.ms_productos.service.ProductoService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoServiceImpl
        implements ProductoService {

    private final ProductoRepository productoRepository;

    private final CategoriaClient categoriaFeignClient;

    private final ProveedorClient proveedorFeignClient;

    @Override
    public ProductoResponseDTO crearProducto(
            ProductoRequestDTO request
    ) {

        log.info("Creando producto SKU: {}",
                request.getSku());

        boolean existe =
                productoRepository.existsBySku(
                        request.getSku()
                );

        if (existe) {

            throw new DuplicateProductoException(
                    "El SKU ya existe"
            );
        }

        CategoriaDTO categoria;
        ProveedorDTO proveedor;

        try {

            categoria =
                    categoriaClient.obtenerCategoria(
                            request.getCategoriaId()
                    );

        } catch (FeignException.NotFound ex) {

            throw new RemoteServiceException(
                    "Categoria no encontrada"
            );
        }

        try {

            proveedor =
                    proveedorClient.obtenerProveedor(
                            request.getProveedorId()
                    );

        } catch (FeignException.NotFound ex) {

            throw new RemoteServiceException(
                    "Proveedor no encontrado"
            );
        }

        Producto producto =
                ProductoMapper.toEntity(request);

        Producto guardado =
                productoRepository.save(producto);

        log.info("Producto creado correctamente ID: {}",
                guardado.getId());

        return ProductoMapper.toDTO(
                guardado,
                categoria,
                proveedor
        );
    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {

        log.info("Listando productos");

        return productoRepository.findAll()
                .stream()
                .map(producto -> {

                    CategoriaDTO categoria =
                            categoriaClient
                                    .obtenerCategoria(
                                            producto.getCategoriaId()
                                    );

                    ProveedorDTO proveedor =
                            proveedorClient
                                    .obtenerProveedor(
                                            producto.getProveedorId()
                                    );

                    return ProductoMapper.toDTO(
                            producto,
                            categoria,
                            proveedor
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductoResponseDTO buscarPorId(Long id) {

        log.info("Buscando producto ID: {}", id);

        Producto producto =
                productoRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductoNotFoundException(
                                        "Producto no encontrado"
                                ));

        CategoriaDTO categoria =
                categoriaClient.obtenerCategoria(
                        producto.getCategoriaId()
                );

        ProveedorDTO proveedor =
                proveedorClient.obtenerProveedor(
                        producto.getProveedorId()
                );

        return ProductoMapper.toDTO(
                producto,
                categoria,
                proveedor
        );
    }

    @Override
    public ProductoResponseDTO actualizarProducto(
            Long id,
            ProductoRequestDTO request
    ) {

        log.info("Actualizando producto ID: {}", id);

        Producto producto =
                productoRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductoNotFoundException(
                                        "Producto no encontrado"
                                ));

        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setSku(request.getSku());
        producto.setPrecio(request.getPrecio());
        producto.setCategoriaId(request.getCategoriaId());
        producto.setProveedorId(request.getProveedorId());
        producto.setEstado(request.getEstado());

        Producto actualizado =
                productoRepository.save(producto);

        CategoriaDTO categoria =
                categoriaClient.obtenerCategoria(
                        actualizado.getCategoriaId()
                );

        ProveedorDTO proveedor =
                proveedorClient.obtenerProveedor(
                        actualizado.getProveedorId()
                );

        log.info("Producto actualizado correctamente");

        return ProductoMapper.toDTO(
                actualizado,
                categoria,
                proveedor
        );
    }

    @Override
    public void eliminarProducto(Long id) {

        log.info("Eliminando producto ID: {}", id);

        Producto producto =
                productoRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductoNotFoundException(
                                        "Producto no encontrado"
                                ));

        productoRepository.delete(producto);

        log.info("Producto eliminado correctamente");
    }
}