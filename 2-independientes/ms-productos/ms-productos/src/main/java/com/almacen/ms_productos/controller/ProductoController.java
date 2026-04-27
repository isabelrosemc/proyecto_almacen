package com.almacen.ms_productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.almacen.ms_productos.model.Producto;
import com.almacen.ms_productos.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // Crear
    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    // Listar todos
    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public Producto obtener(@PathVariable Long id) {
        return productoService.obtener(id);
    }

    // Actualizar
    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
    }

    // Método custom (por categoría)
    @GetMapping("/categoria/{idCategoria}")
    public List<Producto> listarPorCategoria(@PathVariable Long idCategoria) {
        return productoService.listarPorCategoria(idCategoria);
    }

    @GetMapping("/test")
    public String test() {
        return "funciona";
    }
}