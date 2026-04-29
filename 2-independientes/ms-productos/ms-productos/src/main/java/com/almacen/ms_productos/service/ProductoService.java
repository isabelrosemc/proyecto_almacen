package com.almacen.ms_productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.almacen.ms_productos.model.Producto;
import com.almacen.ms_productos.repository.ProductoRepository;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // CREAR
    public Producto crear(Producto producto) {

        // validar nombre duplicado (opcional pero útil)
        if (productoRepository.findAll()
                .stream()
                .anyMatch(p -> p.getNombre().equalsIgnoreCase(producto.getNombre())
                        && p.getMarca().equalsIgnoreCase(producto.getMarca()))) {
            throw new RuntimeException("Ya existe un producto con ese nombre y marca");
        }

        // validar precio
        if (producto.getPrecioVenta() <= 0) {
            throw new RuntimeException("El precio de venta debe ser mayor a 0");
        }

        // validar categoría
        if (producto.getIdCategoria() == null || producto.getIdCategoria() <= 0) {
            throw new RuntimeException("La categoría es obligatoria");
        }

        return productoRepository.save(producto);
    }

    // LISTAR
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // OBTENER POR ID
    public Producto obtener(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con tal id"));
    }

    // ACTUALIZAR
    public Producto actualizar(Long id, Producto productoActualizado) {

        Producto existe = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con tal id"));

        // validar precio
        if (productoActualizado.getPrecioVenta() <= 0) {
            throw new RuntimeException("El precio de venta debe ser mayor a 0");
        }

        // validar categoría
        if (productoActualizado.getIdCategoria() == null || productoActualizado.getIdCategoria() <= 0) {
            throw new RuntimeException("La categoría es obligatoria");
        }

        // actualizar campos
        existe.setNombre(productoActualizado.getNombre());
        existe.setMarca(productoActualizado.getMarca());
        existe.setDescripcion(productoActualizado.getDescripcion());
        existe.setIdCategoria(productoActualizado.getIdCategoria());
        existe.setPrecioVenta(productoActualizado.getPrecioVenta());
        existe.setUnidadMedida(productoActualizado.getUnidadMedida());
        existe.setContenidoNeto(productoActualizado.getContenidoNeto());

        return productoRepository.save(existe);
    }

    // ELIMINAR
    public void eliminar(Long id) {

        Producto existe = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con tal id"));

        productoRepository.delete(existe);
    }

    // LISTAR POR CATEGORÍA
    public List<Producto> listarPorCategoria(Long idCategoria) {
        return productoRepository.findByIdCategoria(idCategoria);
    }
}