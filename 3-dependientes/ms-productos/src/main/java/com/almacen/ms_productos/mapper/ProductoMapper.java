package com.almacen.ms_productos.mapper;

import com.almacen.ms_productos.dto.*;
import com.almacen.ms_productos.model.Producto;

public class ProductoMapper {

    public static Producto toEntity(
            ProductoRequestDTO dto
    ) {

        return Producto.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .sku(dto.getSku())
                .precio(dto.getPrecio())
                .categoriaId(dto.getCategoriaId())
                .proveedorId(dto.getProveedorId())
                .estado(dto.getEstado())
                .build();
    }

    public static ProductoResponseDTO toDTO(
            Producto producto,
            CategoriaDTO categoria,
            ProveedorDTO proveedor
    ) {

        return ProductoResponseDTO.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .sku(producto.getSku())
                .precio(producto.getPrecio())
                .categoria(categoria)
                .proveedor(proveedor)
                .estado(producto.getEstado())
                .fechaCreacion(producto.getFechaCreacion())
                .build();
    }
}